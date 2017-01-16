# Echo
Echo re-write in Java

## Requirements
* IntelliJ 2016
* Java 8
*** note: if machine has WebSense, must be done on TeamCity Test Server (Virtual machine)

## Setup
* Install Java 8
* Install IntelliJ 2016
* Checkout Java Echo
* Ensure "JAVA_HOME" system variable is set to your JDK path in Windows.
* Open IntelliJ IDEA, select "Import Project"
* Navigate to and highlight the "javaecho" directory, press "OK"
* In the Event Log click on the link that states "Import Gradle Project".
* In the dialog box that appears, ensure that "Create seperate module per source set" and "Use gradle wrapper task configuration" are checked
* Press OK in the dialog that appears.
* Press File -> Project Structure.
* Next to the dropdown with the red text press New.
* Select JDK and put the value of the JDK path into dialog.
* Click View -> Tool Windows -> Project to see a Solution Explorer like view of the project.
* If required, navigate to File -> Settings -> Build,Execution,Deployment _> Maven -> Repositories and Update the Remote
* The project will now be buildable.

##Executing Tests
* Navigate to Run -> Edit Configuration
* Select Default COnfiguration -> Junit
* For classpath of module, point it to Echo.Automation.SampleTeam
* For Test Kind, set to directory, and point it to SampleTeam -> src -> test -> java
* Press Apply or OK and JUnit tests should now be executable

##IE Registry Hacks
* For tests to run locally, you need to change the Windows registry.
* [HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl]

  [HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN]
  "iexplore.exe"=dword:00000000

  [HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_LOCALMACHINE_LOCKDOWN\Settings]
  "LOCALMACHINE_CD_UNLOCK"=dword:00000000