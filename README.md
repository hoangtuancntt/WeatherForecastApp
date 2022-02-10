# WeatherForecastApp
I implemented this app by using MVVM pattern, Retrofit2, Dagger Hilt, LiveData, ViewModel, Coroutines, Data Binding...

MVVM facilitates a separation of development of the graphical user interface.

Technologies used:
1. Retrofit a REST Client for Android which makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
2. Dagger Hilt for dependency injection.
3. ViewModel to store and manage UI-related data in a lifecycle conscious way.
4. LiveData to handle data in a lifecycle-aware fashion.
5. Coroutines help in managing background threads and reduces the need for callbacks.
6. Data Binding to declaratively bind UI components in layouts to data sources.

Folders:
1. Data defines the data of the application. This is where we fetch the data, either from a remote database using API calls, or from local database. 
2. DI provides denpendency injection for app
3. UI basically communicates with ViewModel, gets the data and presents it. The view basically observes for changes in the data in the ViewModel and updates the UI to display it to the user.
4. Utils defines some extentions, constants....

Installation:
1. Clone the repository
2. This project requires a minimum API level of 21.

Checklist: 1, 2, 3, 4, 6a, 8
