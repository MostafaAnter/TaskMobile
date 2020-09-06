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

-
### Technolgy stack
* **Progaurd** to shrink resource and obfsicate code to get smallest apk.
* **Groovy** define all dependencies version in on place so we can update it easly.
* **dataBinding** that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
* **Dagger Hilt** for implement DI concept
 
-

###  Step of emplementation:
1. Test API by using Postman
2. proguard
3. 

-


