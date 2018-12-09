# Projet Notre-Dame

[![Build Status](https://travis-ci.org/ApplETS/Notre-Dame.svg?branch=master)](https://travis-ci.org/ApplETS/Notre-Dame)

Ce projet concrétise la troisième version de l'application mobile ÉTSMobile pour Android et iOS. Il s'agit de portail principal entre l'utilisateur et l'[École de technologie supérieure (ÉTS)](https://www.etsmtl.ca/) sur appareils mobiles. ÉTSMobile est un projet open-source développé par les membres du club étudiant [ApplETS](https://clubapplets.ca/). L'application offre notamment :

* L'accès aux notes d'évaluations
* L'accès aux horaires de cours
* Et bien plus...

## 🤖 [Application Android](https://github.com/ApplETS/Notre-Dame/tree/master/android)
* Langage: [Kotlin](https://github.com/ApplETS/Notre-Dame/search?l=kotlin)
* Version du SDK minimale: 21
* [Architecture](https://github.com/ApplETS/Notre-Dame/wiki/Architecture-(FR))
  #### Pour commencer
 * Téléchargez et installez la dernière version d'[Android Studio](https://developer.android.com/studio/)
 * Clonez le projet
 ```bash
git clone https://github.com/ApplETS/Notre-Dame.git
```
 * Ouvez le projet avec Android Studio

## 🍎 [Application iOS](https://github.com/ApplETS/Notre-Dame/tree/master/ios)
* Langage: [Swift](https://github.com/ApplETS/Notre-Dame/search?l=swift)
* Version du SDK minimale: 12.x
  #### Pour commencer
  * Téléchargez et installez la dernière version de [Xcode](https://itunes.apple.com/ca/app/xcode/id497799835?mt=12)
  * Clonez le projet
  ```bash
  git clone https://github.com/ApplETS/Notre-Dame.git
  ```
  * Naviguez à l'intérieur du répertoire pour atteindre le Podfile
  ```bash
  cd Notre-Dame/ios/ETSMobile
  ```
  * Installer les Cocoapods déclarés à l'intérieur du Podfile en exécutant la commande:
  ```bash
  pod install
  ```
  * Un fichier .xcworkspace sera généré. Vous devrez désormais ouvrir le projet avec ce fichier.

## ⚖️ Licence
Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame/blob/master/LICENSE) pour plus d'informations.
