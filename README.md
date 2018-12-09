# Projet Notre-Dame

[![Build Status](https://travis-ci.org/ApplETS/Notre-Dame.svg?branch=master)](https://travis-ci.org/ApplETS/Notre-Dame)

This project is the placeholder for the third version of ÉTSMobile, a mobile which application that is currently available for Android and iOS. ÉTSMobile is the main gateway between the user and the [École de technologie supérieure (ÉTS)](https://www.etsmtl.ca/) on mobile devices. ÉTSMobile is an open-source project and is developped by members of the student club [ApplETS](https://clubapplets.ca/). It offers:

* Access to evaluation grades
* Access to the student's schedules
* And many more...

_Note: This guide is available in: [Français](https://github.com/ApplETS/Notre-Dame/blob/master/README.fr.md)_

## 🤖 [Android Application](https://github.com/ApplETS/Notre-Dame/tree/master/android)
* Language: [Kotlin](https://github.com/ApplETS/Notre-Dame/search?l=kotlin)
* Minimum SDK Version: 21
* [Architecture](https://github.com/ApplETS/Notre-Dame/wiki/Architecture-(EN))
  #### Getting started
 * Download and install the latest version of [Android Studio](https://developer.android.com/studio/)
 * Clone the project
 ```bash
git clone https://github.com/ApplETS/Notre-Dame.git
```
 * Open the entire project in Android Studio

## 🍎 [iOS Application](https://github.com/ApplETS/Notre-Dame/tree/master/ios)
* Language: [Swift](https://github.com/ApplETS/Notre-Dame/search?l=swift)
* Minimum SDK Version: 12.x
 #### Getting started
 * Download and install the latest version of [Xcode](https://itunes.apple.com/ca/app/xcode/id497799835?mt=12)
 * Clone the project
  ```bash
git clone https://github.com/ApplETS/Notre-Dame.git
```
* Navigate inside the directory to reach the Podfile
 ```bash
cd Notre-Dame/ios/ETSMobile
```
* Install the Cocoapods declared inside the Podfile by running the command:
 ```bash
pod install
```
* A .xcworkspace file will be generated. You must use this file to open the project.
 

## ⚖️ License
This projet is licensed under the Apache License V2.0. See the [LICENSE](https://github.com/ApplETS/Notre-Dame/blob/master/LICENSE) file for more info.
