package com.samzuhalsetiawan.latihanmvvm

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertMultipleUser(vararg users: User)

    @Insert
    fun insertListOfUser(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateIfExist(user: User)

    @Update
    fun updateUser(vararg users: User)

    @Update
    fun updateServerWithReturnInfo(vararg users: User): Int

    @Delete
    fun deleteUser(vararg users: User)

    @Query("SELECT * FROM user_table")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM user_table WHERE user_name = :name")
    fun getUserByName(name: String): List<User>

    @Query("SELECT * FROM user_table WHERE user_age = :age")
    fun getUserByAge(age: Int): List<User>

    @Query("SELECT * FROM user_table WHERE user_age BETWEEN :first AND :last")
    fun getUserInAgeRange(first: Int, last: Int): List<User>

    @Query("SELECT * FROM user_table WHERE user_name LIKE :name")
    fun getUserLikeName(name: String): List<User>

    @Query("SELECT user_name, user_age FROM user_table")
    fun getAllUserSimple(): List<UserWithoutId>

    @Query("SELECT user_name, user_age FROM user_table WHERE user_name = :name")
    fun getUserByNameSimple(name: String): List<UserWithoutId>

    @Query("SELECT * FROM user_table WHERE user_age > :minAge")
    fun getUserByMinAge(minAge: Int): List<User>

    @Query("SELECT * FROM user_table WHERE user_name LIKE :searchString OR user_age LIKE :searchString OR uid LIKE :searchString")
    fun findUserByAnything(searchString: String): List<User>

    @Query("SELECT * FROM user_table WHERE user_name IN (:usernames)")
    fun findUserNameByList(usernames: List<String>): List<User>
}