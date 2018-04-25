# Projet Notre-Dame : Android

[![Build Status](https://travis-ci.org/ApplETS/Notre-Dame-Android.svg?branch=master)](https://travis-ci.org/ApplETS/Notre-Dame-Android)

Ce projet concrétise la nouvelle génération de l'application ÉTSMobile pour Android. ÉTSMobile est, tout d'abord, le premier point d'entrée entre l'utilisateur et l'École de technologie supérieure (ÉTS) sur appareils mobiles. Celle-ci offre notamment :

* L'accès aux notes d'évaluations
* L'accès aux horaires de cours
* Et bien plus...

## Prérequis

* Git
* Java Development Kit (JDK)
* Android Studio

## Architecture

L'application utilise l'architecture [MVVM](https://fr.wikipedia.org/wiki/Mod%C3%A8le-vue-vue_mod%C3%A8le) en se reposant sur les [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (AAC). Plus précisément, elle utilise les composantes suivantes : 
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)

<image src="docs/images/etsmobile_architecture.png" width="600" />

Vous pouvez vous renseigner davantage en consultant le [wiki](https://github.com/ApplETS/Notre-Dame-Android/wiki/Architecture-(FR)).

## Bibliothèques et technologies
Ce projet utilise une variété de bibliothèques et de technologies.
* Android Support Libraries
  * AppCompat v7
  * Design
  * Support v4
  * CardView
  * RecyclerView
  * ConstraintLayout
  * AOSP Codes
* Android Architecture Components
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
  * [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Kotlin](http://kotlinlang.org/)
* [JUnit](https://junit.org/)
* [Mockito](http://site.mockito.org/)
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
* [Glide](https://bumptech.github.io/glide/)
* [Dagger2](https://google.github.io/dagger/)
* [Moshi](https://github.com/square/moshi/)
* [Kotshi](https://github.com/ansman/kotshi)
* [Retrofit2](http://square.github.io/retrofit/)
* [Material Design Dimens](https://github.com/DmitryMalkovich/material-design-dimens)
* [LeakCanary](https://github.com/square/leakcanary)
* [Ktlint Gradle](https://github.com/JLLeitschuh/ktlint-gradle)
* Et plus...

## Licence

Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) pour plus d'informations.
