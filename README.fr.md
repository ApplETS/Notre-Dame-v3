<div align="center">
  <img src="https://lh3.googleusercontent.com/9rjfsSfCoglGlZI5xGo684RSQjgC_hOGse1VZXN6l_7ztH0zq-H20Je12Lf_8PLUzUR4=s180-rw" />
  <p>
    <br /><strong>Projet Notre-Dame</strong>
    <br />
    <a href="https://travis-ci.org/ApplETS/Notre-Dame" style="text-decoration: none;">
        <img src="https://travis-ci.com/ApplETS/Notre-Dame.svg?branch=master" alt="Build Status"/>
    </a>
    <br />
  </p>
</div>

Ce projet concrétise la troisième version de l'application mobile ÉTSMobile pour Android et iOS. Il s'agit de portail principal entre l'utilisateur et l'[École de technologie supérieure (ÉTS)](https://www.etsmtl.ca/) sur appareils mobiles. ÉTSMobile est un projet open-source développé par les membres du club étudiant [ApplETS](https://clubapplets.ca/). L'application offre notamment :

* L'accès aux notes d'évaluations
* L'accès aux horaires de cours
* Et bien plus...

## 🤖 [Application Android](https://github.com/ApplETS/Notre-Dame/tree/master/android)

<a href='https://play.google.com/store/apps/details?id=ca.etsmtl.applets.etsmobile.beta&hl=fr_CA'><img alt='Disponible sur Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/fr-ca_badge_web_generic.png' width=250px /></a>

* Langage : [Kotlin](https://github.com/ApplETS/Notre-Dame/search?l=kotlin)
* Version du SDK minimale : 21
  
  #### Pour commencer
 * Téléchargez et installez la dernière version d'[Android Studio](https://developer.android.com/studio/)
 * Clonez le projet
 ```bash
git clone https://github.com/ApplETS/Notre-Dame.git
 ```
 * Ouvez le projet avec Android Studio

## 🍎 [Application iOS](https://github.com/ApplETS/Notre-Dame/tree/master/ios)
* Langage : [Swift](https://github.com/ApplETS/Notre-Dame/search?l=swift) version : 5
* Version du SDK minimale : 12.x
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
  
   ### Compiler l'application
   Exécuter la commande suivante après chaque modification dans le module `shared`
   ```bash
   gradlew packForXCode
   ```
  Puis compiler l'application sur XCode.

## 🤖 / 🍎 Projet Kotlin Multiplatform
Le projet est en cours de migration vers [Kotlin Multiplatform](https://kotlinlang.org/docs/reference/multiplatform.html).

<a href="https://github.com/ApplETS/Notre-Dame/wiki/Project-Architecture-(EN)" style="text-decoration: none;">
    <img src="docs/images/architecture_multiplatform.png" width="600" />
</a>

## :shirt: [Ktlint Gradle](https://github.com/jlleitschuh/ktlint-gradle)
Le projet utilise [Ktlint Gradle](https://github.com/jlleitschuh/ktlint-gradle), un adaptateur (wrapper) de [ktlint](https://ktlint.github.io/). Il s'agit d'un linter et d'un formatteur pour du code rédigé avec le langage Kotlin. Afin de vérifier que le code soit bien formatté, le logiciel d'intégration continue exécute la commande suivante : 
```shell
$ ./gradlew ktlintCheck
```

## 🤔 Question ou problème?

Consultez la [FAQ](https://github.com/ApplETS/Notre-Dame/wiki/Beta-Test-FAQ-(FR)), ouvrez une [issue](https://github.com/ApplETS/Notre-Dame/issues/new/choose) ou envoyez un courriel à l'adresse suivante : info@clubapplets.ca.

## ⚖️ Licence
Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame/blob/master/LICENSE) pour plus d'informations.
