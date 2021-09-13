package com.example.practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import api.FetchError
import api.UsersListItem


// TODO: Display list of users with the user information mentioned in the assignment
// Note: A nice looking UI is appreciated but clean code is more important

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Call fetch user list function
        viewModel.fetchUserList()

        setContent {
            Column {
                TopAppBar(
                    elevation = 4.dp,
                    title = {
                        Text("User List")
                    },
                    backgroundColor = MaterialTheme.colors.primarySurface,
                )

                // Set the default state i.e. Loading
                // No need to handle state specially for lazy column as
                // 'observeAsState' remember state itself
                val userDataState by viewModel.userDataState.observeAsState(MainActivityViewModel.UserDataState.Loading)

                // Check current state of user data and show ui component accordingly
                MainScreen(userDataState = userDataState)
            }
        }

    }

    /**
     * Function check current state of user data and show ui component accordingly
     * @param userDataState : User Data State
     */
    @Composable
    fun MainScreen(userDataState: MainActivityViewModel.UserDataState){
        when (userDataState) {
            is MainActivityViewModel.UserDataState.Loading -> {
                loadingUi()
            }
            is MainActivityViewModel.UserDataState.ShowUsers -> {
                userDataState.usersList.let { items ->
                    if (items != null && items.usersList?.isEmpty() == false)
                        showUserListUi(usersListItems = items.usersList) {
                            viewModel.fetchUserList(excludingUserId = it)
                        }
                    else
                        handleErrorMessage(
                            fetchError = FetchError(
                                IllegalArgumentException(),
                                "No User Found"
                            )
                        )
                }
            }
            is MainActivityViewModel.UserDataState.Error -> {
                handleErrorMessage(fetchError = userDataState.fetchError)
            }
        }
    }

    /**
     * Composable function shows user list ui
     */
    @Composable
    fun showUserListUi(usersListItems: List<UsersListItem?>?, onUserClickEvent: (String) -> Unit){
//        Column {
//            TopAppBar(
//                elevation = 4.dp,
//                title = {
//                    Text("User List")
//                },
//                backgroundColor = MaterialTheme.colors.primarySurface,
//            )
            usersListItems.let {
                if (it?.isEmpty()!!) {
                    Text(text = "No User Found")
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.LightGray,
                        contentColor = Color.Black
                    ) {
                        // Load list of item using lazy column to show list on the screen
                        LazyColumn(contentPadding = PaddingValues(15.dp)) {
                            items(it) { row ->
                                showUserItemUi(
                                    usersListItem = row!!,
                                    onUserClickEvent = onUserClickEvent
                                )
                            }
                        }
                    }
                }
//            }
        }
    }

    /**
     * Composable function show error message
     */
    @Composable
    fun handleErrorMessage(fetchError: FetchError){
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)) {
            Text(text = fetchError.errorMessage!!,
                style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
        }
    }

    /**
     * Composable function show loading ui screen
     */
    @Composable
    fun loadingUi(){
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)) {
            Text(text = "Loading...",
                style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
        }
    }

    /**
     * Composable function show user detail in each row.
     */
    @Composable
    fun showUserItemUi(usersListItem: UsersListItem, onUserClickEvent: (String) -> Unit){
        Log.d("MainActivity", usersListItem.name!!)
        Box {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .clickable(onClick = {
                        onUserClickEvent.invoke(usersListItem.id.toString())
                    })
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${usersListItem.id} - ${usersListItem.name}",
                        style = MaterialTheme.typography.body1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Company - ${usersListItem.company?.name}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Email - ${usersListItem.email}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "City - ${usersListItem.address?.city}",
                        style = MaterialTheme.typography.caption
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}
