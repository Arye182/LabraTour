# "LabraTour" - Final Project

## Description
With our passion to travel and tour the world combining the covid challenges around the world we came up with this idea.
Labratour is our final project on Computer Science Class. nowadays recommendation applications are very popular (netflix, spotify, etc…).
We would like to develop a point of interest recommendation system that will make tourists' lives easier.
Target Clients: Mainly Tourists and People abroad who want to explore new places in their current location.  for any age, gender and beliefs.
Innovation Aspects: “LabraTour” is  an attractions recommendation system in a mobile app  environment , which provides a tourist on his trip
a  customized info and recommendations for points of interest around,   based on his location, his own personal preference and others  with similar interests nearby.

## Problem
Most of the tourists spend a lot of time searching for points of interest nearby them. thinking about what they should wear
today or what to take with them. We want to make these challenges easier with our app.
Most of the apps that suggest this idea have no user based recommendations for pois or a nice and clean user interface.
Nowadays, nobody wants to use tour books and carry them all around.

## Vision
Our Product is For Tourists Who need all the relevant information in one center. “LabraTour” is a 
Mobile App That studies the user and Customizes him the best Points Of Interest he needs based on a Near-By Algorithm.

[Vision Clip](https://drive.google.com/drive/folders/1QB2G8W83gZMo1F9RghH-OQyRx7Rc6yu3)

## Solution
“LabraTour” is  an attractions recommendation system in a mobile app  environment , which provides a tourist on his trip  
a  customized info and recommendations for points of interest around,   based on his location, his own personal preference and others  with similar interests nearby.

## Clean - Architecture

<img src="https://cdn.statically.io/img/miro.medium.com/max/768/1*Xz9N14Fx30za5vggYnkBeA.png" width="400" height="200">

we use clean architecture in our project. all UI and View is in the presentation Module.
we believe in clean code.

- we use clean code - Modular with MVVM extended architecture (usecases etc.)
- dependency injection is made manually
- Presentation is handling only ui issues. it holds bridges to the layer undernith - the domain but does not
  responsible for the buisness logic that happens in there. it is only responsible for the view change!!!!
- Domain is - The domainholds UseCases that allow the VIEW to interact with the data layer and by using the
  execute function in every use case we can actually activate the repositories methods to fetch data. either from local room db or api requests.
- Data is responsible of all the entities, repositories, apis, etc. every thing that related to the sources of the data. in this
  project we used local room db, firebase database, api requests. the data layer is responsible to obtain all those requests in the suitable
  channels.

<img src="file:///android_asset/img/architecture.JPG" width="400" height="400">

### Presentation Module:
- Module - App
- Programming Language - Kotlin + XML
- Kotlin Documentation
- Material Design - for much more responsive and better ui/ux
- 2 main activities - Login and Home - the rest is managed in fragments
- Navigation Component of Android X jetpack
- ViewModel, Shared View Model
- LiveData, MutableLiveData
- StateFlow
- Responsive UI with gestures (focus, clicks, navigation, colors, awareness)
- Observers (lots of)

### Domain Module:
- Module - Domain
- Programming Language - Java
- UseCases
- Repositories
- Reactive JX

### Data Module:
- Module - Data
- Programming Language - Java + Kotlin
- SQLITE ROOM (Local DB)
- API's
- Entities and Data Classes

## APIS:
- [Google Places Api + SDK](https://developers.google.com/maps/documentation/places/web-service/overview)
- [Open Weather Map](https://openweathermap.org/api)
- [Currency Converter](https://api.exchangeratesapi.io/v1/)

## UnitTests (Unit-Tests):
- Miri

## System Requiremnets
- Android API 29 and ABOVE! (RECOMMENDED)

## Installation
- ask us for apk

## Support
- arye.amsalem@gmail.com  
- miriyungreis@gmail.com

## Authors and acknowledgment
- [Arye182 On Github](https://github.com/Arye182)
- [miriyungreis On Github](https://github.com/miriyungreis)

## License
MIT/Apache

## Project status
version 1 - OnProgress
the app will be active until 5/10/2021 due to funds issues

## Git Hub Link
[Project On Github](https://github.com/Arye182/LabraTour)
