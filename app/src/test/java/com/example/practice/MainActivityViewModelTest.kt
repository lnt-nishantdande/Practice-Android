package com.example.practice

import api.UsersListItem
import com.example.practice.Utils.Companion.getJson
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.gson.reflect.TypeToken
import org.junit.Before
import java.lang.reflect.Type

/**
 * Main Activity Test Class is responsible to test main activity logic
 */
@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    /**
     * Declare Main Activity View Model Class variable
     */
    lateinit var mMainActivityViewModel: MainActivityViewModel
    /**
     * Declare Reference user list item list
     */
    lateinit var mReferenceUserListItems: List<UsersListItem>

    @Before
    fun setup(){
        // Initialize MainActivityViewModel Class to access class function
        mMainActivityViewModel = MainActivityViewModel()

        // Create reference user list items list, we have to parse set of user data
        // from 'userlist_items.json' file which is placed in resoucres directory.
        val userListJsonResponse = getJson("userlist_items.json")
        val parsedUserListItems: Type = object : TypeToken<List<UsersListItem?>?>(){}.type

        // Set parsed user list to 'mReferenceUserListItems'
        mReferenceUserListItems = Gson().fromJson(userListJsonResponse, parsedUserListItems)
    }

    /**
     * Function test uses case when no user is excluded
     * Expected Result : 'mReferenceUserListItems' List should have 10 items
     * Actual Result : User List from 'excludeByUserId' api should have 10 items
     */
    @Test
    fun test_main_activity_view_model_exclude_no_user(){
        mMainActivityViewModel.excludeByUserId("", mReferenceUserListItems){ userListItems ->
            Assert.assertEquals(mReferenceUserListItems, userListItems)
        }
    }

    /**
     * Function test use case when we exclude user by id
     * Expected Result : 'mReferenceUserListItems' List should have 9 items
     * Actual Result : User List from 'excludeByUserId' api should have 9 items
     */
    @Test
    fun test_main_activity_view_model_exclude_user_by_id(){
        val usersListExcludeUserOfIdOne = mReferenceUserListItems.filterNot { it?.id == 1 }
        mMainActivityViewModel.excludeByUserId("1", mReferenceUserListItems){ userListItems ->
            Assert.assertEquals(usersListExcludeUserOfIdOne, userListItems)
        }
    }
}