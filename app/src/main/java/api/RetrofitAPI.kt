package api

import kotlinx.coroutines.*
import java.net.UnknownHostException


// TODO: Implement a simple networking API using retrofit or any library you're familiar with 🙌
// Note: Fetching users-list is the *only* use-case therefore avoid implementing unrelated,
// extra, features no matter how common or useful they might be for potential future cases.
//
class RetrofitAPI(private val usersListURL: String) : API {

    /**
     * Declare and Initialize user api service lazily whenever required
     */
    private val userApiService by lazy { NetworkModule.provideUserApi(usersListURL) }

    override fun fetchUsersList(
        excludingUserWithID: String?,
        success: (UsersList) -> Unit,
        failure: (FetchError) -> Unit
    ) {
        // Create coroutine builder to call suspend function
        GlobalScope.launch(Dispatchers.IO) {

            try {
                // Request to get data from 'users' endpoint
                val req = userApiService.fetchUsersList()

                // wait for response and handle api response using 'success' or 'failure' functions
                success.invoke(UsersList(req))
            } catch (e: Exception) {
                e.printStackTrace()
                // Case to handle various network errors
                if (e is UnknownHostException) {
                    // handle case when user has no internet connection
                    failure.invoke(FetchError(e, "Internet Not Available"))
                } else {
                    // handle various general network cases
                    failure.invoke(FetchError(e, e.localizedMessage))
                }
            }
        }
    }

}

