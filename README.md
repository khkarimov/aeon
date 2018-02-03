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

### Aeon Settings

#### Configurations
| **Name**      	| **Type** |**Default**|**Description**  |
|:---|:---:|:---:|:---|
| aeon.browser | String | | Name of the Browser to launch.</br> Accepted entries: "Firefox", "Chrome", "InternetExplorer", "Edge", "IOSSafari", "AndroidChrome", "IOSHybridApp", "AndroidHybridApp" |
| aeon.timeout | long | 10 | Time until timeout after each request. |  
| aeon.throttle | long | 50 | Time between requests. |  

   
#### Web Configurations
| **Name**      	| **Type** |**Default**|**Description**  |
| :---	|:---:|:---:|:---|
| aeon.wait\_for\_ajax\_responses | boolean | true | Set true to wait for ajax responses. | 
| aeon.environment | String | "" | Line 1 </br> Line 2|  
| aeon.protocol | String | "https" | Line 1 </br> Line 2|
| aeon.timeout.ajax | long | 20 | Timeout while waiting for ajax responses. |  
| aeon.browser.maximize | boolean | true | Set true to launch browser maximized. |  
| aeon.scroll\_element\_into\_view | boolean | false | Line 1 </br> Line 2|

#### Selenium Configurations
| **Name** | **Type** |**Default**|**Description**  |
| :---|:---:|:---:|:---|
| aeon.selenium.language| String | "en-us"| Browser accepted language codes.|
| aeon.selenium.move\_mouse\_to\_origin | boolean | false | Set to true to moves mouse to origin of the window on browser launch. |  
| aeon.selenium.use\_mobile\_user\_agent | boolean | false | Line 1 </br> Line 2| 
| aeon.selenium.proxy_location | String | "" | Line 1 </br> Line 2| 
| aeon.selenium.grid.url | String | "" | ?? Url to selenium hub. Must end in "/wd/hub" |
| aeon.selenium.ensure\_clean\_environment | boolean | true | Set to true to launch browser in a clean environment. Deletes cookies, cache, temp files, history, or form data. Each browser may delete different things. |  
| aeon.selenium.chrome.driver | String | null | Path to chromedriver. |  
| aeon.selenium.ie.driver | String | null | Path to IEDriverServer.|
| aeon.selenium.firefox.driver | String | null | Path to geckodriver.|
| aeon.selenium.edge.driver | String | null | Path to MicrosoftWebDriver.|  
| aeon.selenium.chrome.binary | String | null | Path to Chrome binary file to use.|  
| aeon.selenium.firefox.binary | String | null | Path to Firefox binary file to use.|
| aeon.selenium.chrome.headless | boolean | false | Set to true to run Chrome in headless mode |  
| aeon.selenium.chrome.mobile.emulation.device | String | null | Device name to be emulated in Chrome.|  
| **Perfecto Settings** |||  
| aeon.selenium.perfecto.user | String | "" | The name of the user running the operation. |
| aeon.selenium.perfecto.pass | String | "" | The password for the user. |  
| aeon.selenium.perfecto.browser_version | String | "" | The version of the browser. |  
| aeon.selenium.perfecto.token | String | "" | User's personal security token.|
| **Appium Settings** |||  
| aeon.selenium.appium.app | String | "" | The Perfecto repository path to an .ipa or .apk file. </br> Incompatible with browserName. |  
| aeon.selenium.appium.device_name | String | "" | Emulator device name. Ex("iPhone X", "Pixel_XL_API_27") |  
| aeon.selenium.appium.platform_version  | String | "" | OS version for iPhone emulator. |
| aeon.selenium.appium.driver_context | String | "" | Line 1 </br> Line 2|
| aeon.selenium.appium.crosswalkpatch | boolean | true | Set to true to use crosswalkpatch for Android testing. |  
| aeon.selenium.appium.udid | String | "" | Unique device identifier of the connected physical device |  
| aeon.selenium.appium.automation_name | String | Appium | Which automation engine to use - determines the UIElement Class names identified by the automation engine. </br> Use XCUITest if automating iOS 10 or later native applications, see more details. </br> Note: use PerfectoMobile for the Perfecto object tree - uses generic UIElement classes. </br> Accepted entries: "Appium", "XCUITest", "PerfectoMobile"|
| **Android Settings** 	|||  
| aeon.selenium.android.app_package | String | "" | Java package of the Android app you want to run |  
| aeon.selenium.android.app_activity | String | "" |  Activity name for the Android activity you want to launch from your package. This often needs to be preceded by a . (e.g.,.MainActivity instead ofMainActivity) | 
| **IOS Settings** 	|||   
| aeon.selenium.ios.bundle_id | String | "" | Bundle ID of the app under test. Useful for starting an app on a real device or for using other caps which require the bundle ID during test startup |