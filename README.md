# WorkInspector

Library to inspect the Android WorkManager jobs. Like an App inspector, but on a device. 

[![](https://jitpack.io/v/koitharu/workinspector.svg)](https://jitpack.io/#koitharu/workinspector) ![minSdk21](https://img.shields.io/badge/minSdk-21-red) ![Kotlin](https://img.shields.io/github/languages/top/koitharu/workinspector)

### Screenshots

| ![](https://github.com/Koitharu/WorkInspector/raw/master/metadata/images/0.png) | ![](https://github.com/Koitharu/WorkInspector/raw/master/metadata/images/1.png) | ![](https://github.com/Koitharu/WorkInspector/raw/master/metadata/images/2.png) |
|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------|

## Usage

1. Add it to your root build.gradle at the end of repositories:

   ```groovy
   allprojects {
	   repositories {
		   ...
		   maven { url 'https://jitpack.io' }
	   }
   }
   ```

2. Add the dependency

    ```groovy
    dependencies {
        debugImplementation("com.github.koitharu:workinspector:$version")
    }
    ```

   Versions are available on [JitPack](https://jitpack.io/#koitharu/workinspector)

3. Usage in code

   ```kotlin
   context.startActivity(WorkInspector.getIntent(context))
   ```

   or alternatively for the debug-only implementation

   ```kotlin
   if (BuildConfig.DEBUG) {
     val intent = Intent()
     intent.component = ComponentName(context, "org.koitharu.workinspector.WorkInspectorActivity")
     context.startActivity(intent)
   }
   ```