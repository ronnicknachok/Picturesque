# Picturesque
Picturesque demonstrates modern Android Development with **Hilt** for Dependency Injection, **Kotlin Coroutines** for concurrent programming, **Kotlin Flow** for streaming of data, **Jetpack libraries (Room, ViewModel, Paging etc)**, **Jetpack Compose** and **Material 3 Design** for UI and all comes together under the **MVVM architecture**. The repository includes the app's layout, features and functionality, as well as documentation on how to implement use this it for extension of features.

## How Picturesque works
Picturesque uses the **Flickr API** using an **apiKey** to display the photos in the app. You can get an **apiKey** by signing up on Flickr developer website here https://www.flickr.com/services/api/misc.api_keys.html and use the key to run this app.

## How to run Picturesque
To be able to run Picturesque first you need to create a new file in the root of this project and name is **apiKey.properties** (Remember not to check this file into a version control system as it contains the API key).

Once the file is created we need to add the following line of code to the file :
```FLICKR_API_KEY="XXXX"``` where ```"XXXX"``` should be replaced with the value of the API Key you got from registering for Flickr API access.

You can now run the app.

##Demonstration
This is how the app looks when running.



https://user-images.githubusercontent.com/28990386/216254752-84897dbe-9f68-4781-afe3-0ee879f331aa.mp4

