#LabraTour

#Description
With our passion to travel and tour the world combining the covid challenges around the world we came up with this idea.
Labratour is our final project on Computer Science Class. nowadays recommendation applications are very popular (netflix, spotify, etc…).
We would like to develop a point of interest recommendation system that will make tourists' lives easier.
Target Clients: Mainly Tourists and People abroad who want to explore new places in their current location.  for any age, gender and beliefs.
Innovation Aspects: “LabraTour” is  an attractions recommendation system in a mobile app  environment , which provides a tourist on his trip
a  customized info and recommendations for points of interest around,   based on his location, his own personal preference and others  with similar interests nearby.

[comment]: <> (![Alt ScreenShot]&#40;https://github.com/Arye182/LabraTour/blob/master/GitHubPics/app_screen_1.JPG?raw=true "ScreenShot 1"&#41;)

##Clean - Architecture
we use clean architecture in our project. all UI and View is in the presentation Module.
we believe in clean code.

- we use clean code - Modular with MVVM extended architecture (usecases etc.)
- dependency injection is made manually
- Presentation is handling only ui issues. it holds bridges to the layer undernith - the domain but does not
  responsible for the buisness logic that happens in there. it is only responsible for the view change!!!!
- Domain is - .....
- Data is - .....



<img src="https://cdn.statically.io/img/miro.medium.com/max/768/1*Xz9N14Fx30za5vggYnkBeA.png" width="400" height="200">

###Presentation Module:

- Module - App
- Programming Language - Kotlin
- Kotlin Documantation
- Coding Style For Kotlin - KTLINT
- Material Design - for much more responsive and better ui/ux
- 2 main activities - Login and Home - the rest is managed in fragments
- Navigation Component of Android X jetpack
- ViewModel


###Domain Module:

- Module - Domain
- Programming Language - Java
- UseCases
- Repositories
- Reactive JX

###Data Module:

- Module - Data
- Programming Language - Java

##UnitTests (Unit-Tests):


##System Requiremnets


##Installation


##Support
- arye.amsalem@gmail.com
- miriyungreis@gmail.com

##Roadmap
none.

##Contributing
none.

##Authors and acknowledgment
- Arye182
- miriyungreis

##License
MIT

##Project status
on progress

##Git Hub Link
[Project On Github](https://github.com/Arye182/LabraTour)
