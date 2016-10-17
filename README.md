Library to help with logging with Logcat

#### Features

* Enable and disable logging through the Manifest file
* Set the log tag in the Manifest file
* Caters for the following log types
    * Log.d() - Debug
    * Log.w() - Warning
    * Log.e() - Error
    * Log.i() - Information
    * Log.v() - Verbose
* Accepts any number of items of any type per log
* Formats logs based on pre set specifications of the objects
    * The following object types are handled and formatted
        * String
        * Integer
        * Long
        * ArrayList
        * Object[]
        * Custom objects, .toString() will be called
    * The following defaults apply
        * `null` outputs as "--NULL--"
        * Empty results output as "--BLANK--"
        * .toString() is called on any object not specifically handled
        * If anything goes wrong and an exception is thrown, "--UNKOWN TYPE--" will be output

#### Setup

* Update the following to the project `build.gradle`:
```
allprojects {
    repositories {
        jcenter()
        maven {
            url  "http://dl.bintray.com/smileyjoe/lib_develop"
        }
    }
}
```
* Add the dependency to the module `build.gradle`:
```
compile 'za.co.smileyjoedev.lib:debug:0.1.1'
```
* Initialize the library, this can be done anywhere, but is best done in the base `Application` class so the library is available anywhere:
```
Debug.init(getApplicationContext());
```
* Update the `AndroidManifest.xml` file with the following metadata settings:
```
<meta-data android:name="@string/lib_debug_meta_tag" android:value="<log_tag_to_use>"/>
<meta-data android:name="@string/lib_debug_meta_enabled" android:value="true/false"/>
```

#### Usage

* Each log type takes an array of objects that can be mixed:
```
String name = "SmileyJoe";
int number = 1;
float amount = 1.09;

Log.d(name, number, amount);

// Output
SmileyJoe: 1: 1.09
```

