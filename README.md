# Practice-Android

Before starting with this task, create a git repository for the project and add our developer 
github account as a collaborator. This allows for a better technical discussion within git 
to comment and ask questions on the codebase. 

This can be done in github repository page - Settings -> Manage Access -> Invite a collaborator,  
and invite ``nets-dev`` to the repository. 

It is advised to commit the progress of the task and create a pull request with ``nets-dev`` 
as reviewer when the task is complete. 

Please actively participate in the pull request review discussion during the review period
which will take place in the github pull request comments section. 

## Task description in order of importance:

(Please reach out if any part of the task description is unclear, we'll be happy to elaborate)

### Use:

* Kotlin 
* Jetpack Compose 
* AndroidX 

---

* Git - short commits at each stage of the project progress 
* Code comment - add code comment when necessary (Do not comment everything)
* Indentation - according to Kotlin official guidelines/conventions 
* Naming (properties, functions, classes…) - according to Kotlin official guidelines/conventions 

### Summary: 

The app should show list of users in rows. 
(This list is retrieved from: [https://jsonplaceholder.typicode.com/users](https://jsonplaceholder.typicode.com/users) )

Each row should show: id, name, email, city and company name.
**Note:** Do not include any other information, e.g. phone number, zipcode...

Upon selecting a row from the list,
the app should re-fetch the list and exclude the user on the selected row.
e.g. If a user with id = 3 is selected, exclude this user from fetch result.
Subsequently, if a user with id = 1 is selected, exclude only this user
(and not the previously selected user with id = 3)

Internally, the network API should always return fetched users list in reverse order.
Consequently, the UI should show last row users at the top of the list.

**Note:** The following files - API.kt, RetrofitAPI.kt and MainActivity.kt contain TODOs to guide 
the implementation and further clarify the task. 

### Task 1. Implement a simple REST client that asynchronously fetches JSON 

Fetch a users list from source: `https://jsonplaceholder.typicode.com/users`.

You may use any networking library (such as Retrofit, Ktor…); however, the implementation should not
contain any unused code, e.g. from another project or template. 

This project contains an ``API`` with a single `fetchUsersList` contract (shown below)
to **isolate** networking implementation detail, this should not be modified. 

```
// Asynchronously fetch users list and return the response in reverse order than received. 
// Upon failure, return the error through the `failure` callback.
// Use the `excludingUserWithID` to filter out an unwanted user from the list. 
//
fun fetchUsersList(
    excludingUserWithID: String? = null,
    success: (UsersList) -> Unit,
    failure: (FetchError) -> Unit
)
```

* Use Kotlin's data-class to deserialize the JSON response
* After obtaining the users list, always return the list to the caller in reverse order
* If given a user ID to exclude, filter out the excluded user ID before returning the result

### Task 2. Write unit tests for the units you see need testing

**Note:** Make sure to write at least 2 test cases 

---
---

## The above two are the main tasks. All tasks below are bonus points

### Task 3 (Bonus). In MainActivity, create UI that presents list of users in rows

The UI should show all list of users received from the API without
filtering or changing order. i.e. there should be no logic in the UI

### Task 4 (Bonus). Discuss / design a better data-type for network request errors

The network request can fail for different reasons. Discuss what could cause
a failure and how could we design an error object to help identify the nature
of a particular failure case.
What data type (class, enum…) can be used to better design such error object?
