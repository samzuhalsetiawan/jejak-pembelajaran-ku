package com.example.githubuser.data.models

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserTest {

    private lateinit var dummyListOfUser: List<User>

    @Before
    fun before() {
        dummyListOfUser = List(10) { i ->
            User(
                id = 100000 + i,
                login = "login_$i",
                avatarUrl = "avatar_url_$i",
                name = "name_$i",
                htmlUrl = "html_url_$i",
                email = "email_$i",
                isFavorite = false
            )
        }
    }

//    I want two instance of user is same if at least some attribute are same
    @Test
    fun testEquality() {
        val dummyUser1 = dummyListOfUser[5]
        val dummyUser2 = dummyUser1.copy(isFavorite = true)
        assertEquals(dummyUser1, dummyUser2)

        val dummyUser3 = dummyUser1.copy(login = "random_string")
        assertNotEquals(dummyUser1, dummyUser3)

        val dummyUser4 = dummyUser1.copy(999999)
        assertNotEquals(dummyUser1, dummyUser4)
    }

}