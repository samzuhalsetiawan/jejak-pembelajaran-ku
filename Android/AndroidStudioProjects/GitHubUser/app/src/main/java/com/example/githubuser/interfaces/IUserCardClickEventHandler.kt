package com.example.githubuser.interfaces

import android.view.View
import com.example.githubuser.data.models.User

interface IUserCardClickEventHandler {

    fun onCardClickListener(view: View, user: User)

    fun onFavoriteIconClickListener(view: View, user: User)

}