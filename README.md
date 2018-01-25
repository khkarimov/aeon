# Aeon

### Contributing

Please have a look at [Contributing](CONTRIBUTING.md) page.


### Requirements
* Latest version of IntelliJ
* Java 8 SDK


### Setup
* Install Java 8 SDK
* Install IntelliJ
* Clone Aeon repository
* Open IntelliJ IDEA, select `Open Project`
* Navigate to and highlight the "aeon" directory, press `OK`
* Press `File` -> `Project Structure`
* Next to the dropdown with the red text press `New`
* Select `JDK` and put the value of the JDK path into the dialog
* Click `View` -> `Tool Windows` -> `Project` to see a Solution Explorer like view of the project
* Click `View` -> `Tool Windows` -> `Gradle` to see a Gradle Project Window
* On the Gradle window click the refresh button
* Expand `Aeon` -> `Tasks` -> `Build` and run `assemble`


### IE Settings
* IE settings have to be adjusted for the tests to run correctly
* Open `Internet Explorer` and click the settings button in the top right -> click `Internet options` -> click the `Security` tab
* Click `Reset all zones to default level` and turn off protected mode in all 4 zones


### IE Registry Hacks
* For tests to run locally, you need to change the Windows registry
* Not sure whether it has to be local machine or current user, but it has to be one or the other (definitely not both)

```
[HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN]
  "iexplore.exe"=dword:00000000

[HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE]
  "iexplore.exe"=dword:00000000

[HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN\Settings]
  "LOCALMACHINE_CD_UNLOCK"=dword:00000000

[HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN]
  "iexplore.exe"=dword:00000000

[HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN\Settings]
  "LOCALMACHINE_CD_UNLOCK"=dword:00000000
```

