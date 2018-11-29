# Projet Notre-Dame : Android

This project is the placeholder for the next generation of ETSMobile currently available for Android.

_Note: This guide is available in: [Français](https://github.com/ApplETS/Notre-Dame/blob/master/android/README.fr.md)_

## Prerequisites

* Git
* Java Development Kit (JDK)
* Android Studio

## Architecture

This application uses an architecture based on the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (AAC). Specifically, it uses the following components: 
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)

<image src="docs/images/etsmobile_architecture.png" width="600" />

You can find out more by visiting the [wiki](https://github.com/ApplETS/Notre-Dame-Android/wiki/Architecture-(EN)).

## Libraries and tools
This project uses a variety of libraries and tools.
* Android Support Libraries : A set of libraries that provide features that are unavailable in the the standard framework as well as backward compatibility classes for newer APIs.
  * AppCompat v7
  * Design
  * Support v4
  * CardView
  * RecyclerView
  * ConstraintLayout
  * AOSP Codes
* Android Architecture Components : A collection of libraries that help developers design robust, testable, and maintainable apps.
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
  * [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Kotlin](http://kotlinlang.org/) : Computer language used for the development of this project
* [JUnit](https://junit.org/) : Unit testing framework
* [Mockito](http://site.mockito.org/) : Testing framework that allows the creation of mock objects
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) : Library for simulating HTTP responses
* [Glide](https://bumptech.github.io/glide/) : Library for loading and caching images
* [Dagger2](https://google.github.io/dagger/) : Dependency injection framework
* [Moshi](https://github.com/square/moshi/) : Library for parsing JSON into Java objects
* [Retrofit2](http://square.github.io/retrofit/) : Type-safe HTTP client
* [Material Design Dimens](https://github.com/DmitryMalkovich/material-design-dimens) : Library containing default colors and dimensions per Material Design guidelines and Android Design guidelines
* [LeakCanary](https://github.com/square/leakcanary) : A memory leak detection library
* [Ktlint Gradle](https://github.com/JLLeitschuh/ktlint-gradle) : Wrapper plugin over the ktlint project. Ktlint provides rules that enforce coding standards.
* And more...

## License

This projet is licensed under the Apache License V2.0. See the [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) file for more info.
