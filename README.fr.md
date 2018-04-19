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

| Modules | Responsabilités |
|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Activity/Fragment | Les Activity et les Fragment prennent en charge l’interface graphique. Ils présentent notamment des données obtenues à partir d'un LiveData. Le fonctionnement est celui du patron Observateur. À leur création, l’activité ou le fragment s'abonne à un LiveData fournie par le ViewModel. Lors d’un changement, le LiveData avertira son observateur qui pourra alors mettre à jour l’interface graphique. |
| [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html) | Les LiveData sont des objets servant à contenir des données. Ils peuvent être observés par des [LifecycleOwner](https://developer.android.com/reference/android/arch/lifecycle/LifecycleOwner.html) tels que les activités et les fragments. Les LiveData respectent le cycle de vie de leurs observateurs et les informent des changements seulement si ceux-ci sont actifs. |
| [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html) | Les ViewModel fournissent des données à des activités ou des fragments. Les ViewModel contiennent des LiveData et permettent à celles-ci de survivre aux changements de configuration. Les LiveData sont obtenus à partir de modules de Repository. |
| Repository | Les modules de Repository ont la responsabilité de récupérer des données et de les retourner sous forme de LiveData aux ViewModel. Pour ce faire, ils agissent en tant que médiateur entre la BD et les API. Tout d’abord, ils doivent tenter d’obtenir les données contenues dans la base de données. Celles-ci sont retournées si elles existent. Cela permet à l’interface graphique de les afficher en attendant la récupération des nouvelles données provenant d’un module d’API. Après l’obtention des nouvelles données, le Repository met à jour la base de données et les nouvelles données sont acheminées vers l’interface graphique. |
| [Resource](https://developer.android.com/topic/libraries/architecture/guide.html#addendum) | Les données acheminées à partir d’un module de Repository vers l’interface graphique sont contenues dans la classe Resource. Cette dernière a la responsabilité d’indiquer le statut associé à ces données. Ceci permet aux observateurs de savoir si le chargement des données a réussi, est en cours ou a échoué. |
| DB | La base de données utilise la librairie [Room](https://developer.android.com/training/data-storage/room/index.html) afin de persister les données de l’application. Les données sollicitées sont retournées sous la forme d’un LiveData. |
| API | Les modules d'API ont la responsabilité de transmettre des requêtes à un service web afin de récupérer les données. Pour ce faire, les modules d'API utilisent la librairie [Retrofit](http://square.github.io/retrofit/). Cette dernière retourne un objet de la classe [Retrofit2.Call](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html). Cet objet est adapté en LiveData avant d’être retourné. |
| ApiResponse | Un ApiReponse représente une réponse retournée par un service web. ApiResponse permet la conversion des objets de la classe [Retrofit2.Call](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html) en LiveData. |

## Licence

Ce projet est licencié selon la licence Apache V2.0. Veuillez consulter le fichier [LICENSE](https://github.com/ApplETS/Notre-Dame-Android/blob/master/LICENSE) pour plus d'informations.
