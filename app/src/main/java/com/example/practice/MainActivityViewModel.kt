package com.example.practice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Class maintain user data using viewModel
 */
class MainActivityViewModel : ViewModel() {

    /**
     * Variable to maintain excluding user id state
     */
    var excludingUserId : Int = 0

    /**
     * Maintain user data state using live data
     */
    val userDataState: LiveData<UserDataState>
        get() = _userDataState
    private val _userDataState: MutableLiveData<UserDataState> = MutableLiveData()

    /**
     * Function fetch user from api call,
     * reversing the order of the items by user id
     * and update user data state using livedata
     * @param excludingUserId : required user id which needs to exclude in String,
     *          empty value of 'excludingUserId' return complete user list
     */
    fun fetchUserList(excludingUserId: String = ""){

        // Call Network Api to get user data
        API.create().fetchUsersList(excludingUserId, {

            // Perform task in coroutine as operation it time consuming
            viewModelScope.launch(Dispatchers.IO) {
                // Reverse order of the data
                val reversed = it.usersList?.reversed()

                // Exclude the user list and return user data list
                excludeByUserId(excludingUserId, reversed){ items ->
                    it.apply {
                        this.usersList = items
                    }
                    viewModelScope.launch(Dispatchers.Main) {
                        if (it.usersList.isNullOrEmpty()){
                            _userDataState.value = UserDataState.Error(FetchError(IllegalArgumentException(), "Not Data Available"))
                        } else {
                            _userDataState.value = UserDataState.ShowUsers(it)
                        }
                    }
                }
            }
        }, {
            _userDataState.value = UserDataState.Error(it)
        })
    }

    /**
     * Function exclude user from user data list by 'id'
     * @param id : user id which needs to exclude
     * @param usersList : list of actual user list
     * @param success : function callback to return result list
     */
    fun excludeByUserId(id: String, usersList: List<UsersListItem?>?,
                        success: (List<UsersListItem?>?) -> Unit){
        if (id == ""){
            success.invoke(usersList)
        } else {
            excludingUserId = id.toInt()
            val items = usersList?.filterNot {
                it.let {
                    if (it?.id == null)
                        false
                    else
                        it.id == excludingUserId
                }
            }
            success.invoke(items)
        }
    }

    /**
     * Class is used to maintain state for Ui
     * loading : Show Loading State
     * ShowUsers : Show User on Ui Screen
     * Error : Handle error if any
     */
    sealed class UserDataState {
        object Loading : UserDataState()
        data class ShowUsers(val usersList: UsersList) : UserDataState()
        data class Error(val fetchError: FetchError) : UserDataState()
    }

}