# macOS Build Agent Setup

1. Verify the agent is on Sierra or newer (skip to step 4, if it is already):
```bash
$ sw_vers
ProductName:	Mac OS X
ProductVersion:	10.12.6 <- This line should be 10.12.x or greater
BuildVersion:	16G1408
```

2. Resize the active disk to have at least 1GB free space.
```bash
$ diskutil list
/dev/disk0 (internal, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:      GUID_partition_scheme                        *80.5 GB    disk0
   1:                        EFI EFI                     209.7 MB   disk0s1
   2:                  Apple_HFS Untitled                79.2 GB    disk0s2 
   3:                 Apple_Boot Recovery HD             650.0 MB   disk0s3
```
Look for the partition with the OS installation on it (usually the largest one). In the above example, this partition is `disk0s2` (look under the `Identifier` column)

#### Note
> Do not skip this step if there is not a similiar disk to `disk0s3` of the above command. The installer will refuse to run.

Now, actually resize the partition. Note: this implicitly entails that resulting free space created will be behind the new end of the partition.
```bash
$ diskutil resizeVolume <partition id> <current size of parition - 1>GB
```

3. [Download](https://itunes.apple.com/us/app/macos-sierra/id1127487414?ls=1&mt=12), open, and install the macOS Sierra.

4. Install all available updates (not including OS upgrades)

5. Configure automatic login.
    1. Open System Preferences
    1. Goto Users & Groups
    1. Unlock the page
    1. Open Login Options
    1. Enable Automatic Login for the build user

6. Display sleep and turning the display off
    1. Open System Preferences
    1. Goto Energy Saver
    1. Set both sliders to Never

7. Disable the password for users in the admin group. (**Note** This command opens `vim`; if you don't know what that is, please familiar yourself with the basics of vim before continuing)
```bash
$ sudo visudo
```

Inside the file, change the following line:
```
%admin          ALL = (ALL) ALL
```
to
```
%admin          ALL = (ALL) NOPASSWD: ALL
```

Save and close the file

#### Note
> This article assumes that the TeamCity Build Agent is already installed in ~/teamcity. ~ is the home directory of the build agent user.

8. Install the build agent plist file
```bash
$ cp ~/teamcity/bin/jetbrains.teamcity.BuildAgent.plist ~/Library/LaunchAgents
```

##### Warning
> You can configure to start build agent on system boot, and place plist file to /Library/LaunchDaemons directory. But in this case there could **be some troubles with running GUI tests**, and with build agent auto-upgrade. So we do not recommend this approach. [Source](https://confluence.jetbrains.com/display/TCD10/Setting+up+and+Running+Additional+Build+Agents#SettingupandRunningAdditionalBuildAgents-AutomaticAgentStartunderMacOSx)

9. Disable session restore on reboot
```bash
$ sudo chown root:admin ~/Library/Preferences/ByHost/com.apple.loginwindow.*
$ sudo chmod 1000 ~/Library/Preferences/ByHost/com.apple.loginwindow.*
```

10. Reboot the computer, and verify the agent automatically connects to the TeamCity build servers

11. Test the following commands from the build user's account:
```bash
# Safari should open, exit code should be 0, nothing should be printed to the terminal
$ open -a Safari
# The following should exit with error code 0 (echo $?), take a few milliseconds, and should not ask for a password
$ sudo safaridriver --enable
# The following should exit upon syscalls SIGTERM (killall safaridriver) and SIGINT (<ctrl>-C) with error code 0 (echo $?), and should not exit automatically
$ safaridriver -p 0
```

12. Test your build, and celebrate if everything works!

### Troubleshooting
`sudo safaridriver --enable` or `safaridriver -p 0` exits with error code 1.
> Log in via a GUI, and retry the respective commands. If they work, logout of the GUI and retry the commands via your terminal session.
