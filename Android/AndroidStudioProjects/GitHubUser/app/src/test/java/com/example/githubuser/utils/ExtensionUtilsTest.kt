package com.example.githubuser.utils

import com.example.githubuser.data.models.User
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExtensionUtilsTest {

    private lateinit var dummyList1: List<User>
    private lateinit var dummyList2: List<User>


    @Before
    fun before() {

        println("\n=============INITIALIZE DUMMY DATA==================")


        dummyList1 = List(10) { i ->
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

        dummyList2 = dummyList1
            .filter { it.id % 2 == 0 }
            .map { it.copy() }
            .onEach { it.isFavorite = true }

        assertTrue(dummyList1.none { it.isFavorite })
        assertTrue(dummyList2.all { it.isFavorite })

        println("\n=============DUMMY LIST 1==================")
        println(dummyList1.map { it.isFavorite })
        println("=============END DUMMY LIST 1==================\n")

        println("\n=============DUMMY LIST 2==================")
        println(dummyList2.map { it.isFavorite })
        println("=============END DUMMY LIST 2==================\n")

        println("=============INITIALIZE DUMMY DATA FINNISH==================\n")

    }

    @Test
    fun testIfMapOperationSuccess1() {
        println("\n=============TEST 1==================")
        println(
            """
             |  Unit Tested:     [ExtensionUtils::List<User>.mapBasedOnFavoriteWith]
             |  Pass Condition:  Semua Element List2 harus ada di List1
           """.trimMargin()
        )


        val outputList = dummyList1.mapBasedOnFavoriteWith(dummyList2)
        assertTrue(outputList.containsAll(dummyList2))

        println("\n=============RESULT==================")
        println("list1 : " + dummyList1.map { it.isFavorite })
        println("list2 : " + dummyList2.map { it.isFavorite })
        println("output: " + outputList.map { it.isFavorite })
        println("=============END RESULT==================\n")

        println("=============TEST 1 FINNISH==================\n")
    }

}