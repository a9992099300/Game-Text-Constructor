# Compose Multiplatform Application

Application for create books in desktop and view in mobile devices.

## Libs:
- ktor
- kotlinx-serializatio
- decompose
- kodein
- kstore
- firebase api

## Screenshots:
![Alt text]https://github.com/a9992099300/Game-Text-Constructor/blob/master/screenshots/Simulator%20Screenshot%20-%20iPhone%2014%20-%202023-09-23%20at%2021.32.12.png?raw=true
![Alt text]https://github.com/a9992099300/Game-Text-Constructor/blob/master/screenshots/Снимок%20экрана%202023-09-23%20в%2021.27.01.png?raw=true

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 8 on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there
 - run `./gradlew podInstall` in the project root

### Android
To run the application on a
ndroid device/emulator:  p
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio

### Browser
Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun`

