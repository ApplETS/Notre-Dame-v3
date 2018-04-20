# Projet Notre-Dame : Android

[![Build Status](https://travis-ci.org/ApplETS/Notre-Dame-Android.svg?branch=master)](https://travis-ci.org/ApplETS/Notre-Dame-Android)

This project is the placeholder for the next generation of ETSMobile currently available for Android. ETSMobile is first and foremost
the main way between the user and the École de technologie supérieure (ÉTS) on mobile devices. It offers:

* Access to evaluation grades
* Access to the student's schedules
* And many more...

_Note: This guide is available in: [Français](https://github.com/ApplETS/Notre-Dame-Android/blob/master/README.fr.md)_

## Prerequisites

* Git
* Java Development Kit (JDK)
* Android Studio

## Architecture

This application uses a [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture by relying on the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (AAC). Specifically, it uses the following components: 
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)

<image src="docs/images/etsmobile_architecture.png" width="600" />

You can find out more by visiting the [wiki](https://github.com/ApplETS/Notre-Dame-Android/wiki/Architecture-(EN)).

## License

This projet is licensed under the Apache License V2.0. See the [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) file for more info.
