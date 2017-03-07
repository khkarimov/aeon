# Aeon
Aeon re-write in Java

## Requirements
* IntelliJ 2016
* Java 8
*** note: if machine has WebSense, must be done on TeamCity Test Server (Virtual machine)

## Setup
* Install Java 8
* Install IntelliJ 2016
* Checkout Java Aeon
* Ensure "JAVA_HOME" system variable is set to your JDK path in Windows.
* Open IntelliJ IDEA, select "Import Project"
* Navigate to and highlight the "javaaeon" directory, press "OK"
* In the Event Log click on the link that states "Import Gradle Project".
* In the dialog box that appears, ensure that "Create seperate module per source set" and "Use gradle wrapper task configuration" are checked
* Press OK in the dialog that appears.
* Press File -> Project Structure.
* next to the dropdown with the red text press New.
* Select JDK and put the value of the JDK path into dialog.
* Click View -> Tool Windows -> Project to see a Solution Explorer like view of the project.
* If required, navigate to File -> Settings -> Build,Execution,Deployment _> Maven -> Repositories and Update the Remote
* The project will now be buildable.
* Click View -> Tool Windows -> Gradle to see a Gradle Project Window
* Expand Aeon -> Tasks -> Build and run assemble

##Executing Tests
* Navigate to Run -> Edit Configuration
* Select Default COnfiguration -> Junit
* For classpath of module, point it to Aeon.Automation.SampleTeam
* For Test Kind, set to directory, and point it to SampleTeam -> src -> test -> java
* Press Apply or OK and JUnit tests should now be executable

##IE Settings
* IE settings have to be adjusted for the tests to run correctly.
* Open Internet Explorer -> Click the settings button in the top right -> click internet options -> click the security tab
* click reset all zones to default level -> turn off protected mode in all 4 zones


##IE Registry Hacks
* For tests to run locally, you need to change the Windows registry.
* Not sure whether it has to be local machine or current user, but it has to be one or the other (definitely not both)

* [HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN]
  "iexplore.exe"=dword:00000000

  [HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE]
  "iexplore.exe"=dword:00000000

  [HKEY_LOCAL_MACHINE\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN\Settings]
  "LOCALMACHINE_CD_UNLOCK"=dword:00000000

* [HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN]
  "iexplore.exe"=dword:00000000

  [HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN\Settings]
  "LOCALMACHINE_CD_UNLOCK"=dword:00000000
