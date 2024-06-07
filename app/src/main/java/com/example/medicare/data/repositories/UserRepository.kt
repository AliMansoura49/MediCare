package com.example.medicare.data.repositories

import com.example.medicare.data.model.user.User

interface UserRepository {
    /** Add a new user to the database with the same ID as the current user*/
    suspend fun addNewUser(user: User)
    /** Get a user from the database by its ID*/
    suspend fun getUser() : User?
}