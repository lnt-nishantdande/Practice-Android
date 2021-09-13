package api

import com.google.gson.annotations.SerializedName

/**
 * Kotlin Data Class to Parse Json Response for User List Api
 */
data class UsersList(

	@field:SerializedName("UsersList")
	var usersList: List<UsersListItem?>? = null
)

data class Address(

	@field:SerializedName("zipcode")
	val zipcode: String? = null,

	@field:SerializedName("geo")
	val geo: Geo? = null,

	@field:SerializedName("suite")
	val suite: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("street")
	val street: String? = null
)

data class UsersListItem(

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: Company? = null,

	@field:SerializedName("id")
	var id: Int = 0, // remove id integer nullable as id never be null. Set default value to '0'.

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class Geo(

	@field:SerializedName("lng")
	val lng: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
)

data class Company(

	@field:SerializedName("bs")
	val bs: String? = null,

	@field:SerializedName("catchPhrase")
	val catchPhrase: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
