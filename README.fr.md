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

L'architecture de l'application repose sur les [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (AAC). Plus précisément, elle utilise les composantes suivantes : 
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)

<image src="docs/images/etsmobile_architecture.png" width="600" />

## Licence

Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) pour plus d'informations.
