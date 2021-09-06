package api

// A networking API used within the app.
//
// Implement the API using retrofit, ktor or any networking library of your choice
// without modifying this API. See `RetrofitAPI.kt` template to start with.
//
// Note: The concrete implementation of this API should not contain anything that's unrelated
//       to this task. There is no plan to expand this project therefore features that are not
//       related to the task should be avoided.
//       i.e. Do not add support for fetching images or anything unrelated to the task at hand.
//
interface API {

    // Execute asynchronous fetch of users list and callback with `success` or `failure` accordingly
    // Caller can choose to exclude a given user by an ID in `excludingUserWithID` parameter.
    // In this case, filter the given ID from the response
    //
    // Note: The task requires result of this fetch to always be returned in reverse order
    //       of the raw JSON response.
    //
    fun fetchUsersList(
        excludingUserWithID: String? = null,
        success: (UsersList) -> Unit,
        failure: (FetchError) -> Unit
    )

    companion object {
        // TODO: Instantiate an API object as follows to use within the app
    }

}

// TODO: Create a data type representing users-list (according to expected JSON response)
// (See the JSON response at: https://jsonplaceholder.typicode.com/users)
//
// Make sure to limit the data inside this data-type to whats required in the assignment.
// Do not include any other information, e.g. phone number, zipcode... if its not required
//
typealias UsersList = Any

// TODO (Bonus): Create a more specific error type.
// This can help identify the nature of a particular failure case.
// e.g. network timeout, badly formatted request or failing to decode/deserialize
// a response could cause failure in a network request.
//
typealias FetchError = Any

