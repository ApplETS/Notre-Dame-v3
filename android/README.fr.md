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

L'application utilise une architecture se reposant sur les [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (AAC). Plus précisément, elle utilise les composantes suivantes : 
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)

<image src="docs/images/etsmobile_architecture.png" width="600" />

Vous pouvez vous renseigner davantage en consultant le [wiki](https://github.com/ApplETS/Notre-Dame-Android/wiki/Architecture-(FR)).

## Bibliothèques et outils
Ce projet utilise une variété de bibliothèques et d'outils.
* [Android Support Libraries](https://developer.android.com/topic/libraries/support-library/) Librairies facilitant le développement en fournisant des classes et des fonctionnalités indisponibles dans l'API standard. En plus de fournir des classes utilitaires, elles fournissent, notamment, une rétrocompatiblité pour les nouvelle versions d'API.
  * AppCompat v7
  * Design
  * Support v4
  * CardView
  * RecyclerView
  * ConstraintLayout
  * Testing Support Library
  * AOSP Codes
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) Collection de librairies permettant de faciliter la conception architecturale d'applications Android. Elle permet aux développeurs de concevoir des applications robustes, testables et maintenables. 
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
  * [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Kotlin](http://kotlinlang.org/) Langage informatique utilisé pour le développement de ce projet
* [JUnit](https://junit.org/) Framework de test unitaire
* [Mockito](http://site.mockito.org/) Framework permettant la création de doubles d'objets (mocks) ainsi que de simuler leurs comportements pour des fins de test
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) Librairie permettant de simuler des réponses HTTP
* [Glide](https://bumptech.github.io/glide/) Librairie permettant le chargement et la mise en cache d'images 
* [Dagger2](https://google.github.io/dagger/) Framework d'injection de dépendances
* [Moshi](https://github.com/square/moshi/) Librairie permettant la conversion de JSON en objets Java et vice-versa
* [Retrofit2](http://square.github.io/retrofit/) Client HTTP offrant le bénéfice de sûreté du typage
* [Material Design Dimens](https://github.com/DmitryMalkovich/material-design-dimens) Librairie contenant les couleurs et les dimensions spécifiées par les directives du [Material Design](https://material.io/guidelines/)
* [LeakCanary](https://github.com/square/leakcanary) Librairie de détection de fuites de mémoire
* [Ktlint Gradle](https://github.com/JLLeitschuh/ktlint-gradle) Plug-in permettant l'intégration de ktlint, une librairie d'analyse du code source Kotlin. Ce dernier contient des règles qui assurent le respect de conventions de codage.
* Et plus...

## Licence

Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) pour plus d'informations.
