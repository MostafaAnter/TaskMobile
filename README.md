# App Task
______________

**Attachment**
* [APK File](https://drive.google.com/file/d/1Palv43AiqQTbeoxh0fm1WtEjVzGO9Ok2/view?usp=sharing)

**features:**

* Search Bank Data
* Validate BIC
* Validate IBAN 
* Validate PostCode Inputs

As a user, I want to navigate throwgh app features so I'm click on bottom navigation drawer, Then select the service that I want to user.

When select Search bank service you will find search field so you can enter Bank routing conde and if it valid code you will get a result. in fact other services work with same manner.

___________
### Support Matireal Design (DARK-Light) Themes
<img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141107.png" width="300"> <img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141123.png" width="300"> <img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141202.png" width="300">


<img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141220.png" width="300"> <img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141231.png" width="300"> <img src="https://raw.githubusercontent.com/MostafaAnter/TaskMobile/master/device-2020-09-06-141254.png" width="300">


 
____________
### Techinecal Architecture
* Model-View-ViewModel (ie MVVM)

**Why Promoting MVVM VS MVP:**

ViewModel has Built in LifeCycleOwerness, on the other hand Presenter not, and you have to take this responsiblty in your side.

ViewModel doesn't have a reference for View, on the other hand Presenter still hold a reference for view, even if you made it as weakreference.

ViewModel survive configuration changes, while it is your own responsiblities to survive the configuration changes in case of Presenter. (Saving and restoring the UI state)

______________
### Technolgy stack
* **Progaurd** to shrink resource and obfsicate code to get smallest apk.
* **Groovy** define all dependencies version in on place so we can update it easly. and of course define cretical string data inside gradle file
* **dataBinding** that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
* **Dagger Hilt** dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in my project.
* **Android jetpack Viewmodel** class is designed to store and manage UI-related data in a lifecycle conscious way.
* **Android jetpack LiveData** is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities
* **Retrofit** for network operations.
* **RXJava** for using reactive programming to enhance network methods such as manage disposables Object by insert it inside compositDisposable
* **EventBus** simplifies the communication between components i used it to transfere data from country selectionDialog to Validate Post Code Fragment.
* **Junit** to implement test cases for improve Validation class that used in validate bank routing code, BIC and IBAN
 
______________

###  Step of emplementation:
1. Test API by using Postman
2. start structing project folders and add dependencies in gradle file
3. after that i decid to start to implement ui layer based on dummy data
4. then implement data layer 

_______________


