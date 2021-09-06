package api


// TODO: Implement a simple networking API using retrofit or any library you're familiar with 🙌
// Note: Fetching users-list is the *only* use-case therefore avoid implementing unrelated,
// extra, features no matter how common or useful they might be for potential future cases.
//
class RetrofitAPI(private val usersListURL: String) : API {

    override fun fetchUsersList(
        excludingUserWithID: String?,
        success: (UsersList) -> Unit,
        failure: (FetchError) -> Unit
    ) {
        print("Fetch users list from $usersListURL")
        TODO("Not yet implemented")
    }

}

