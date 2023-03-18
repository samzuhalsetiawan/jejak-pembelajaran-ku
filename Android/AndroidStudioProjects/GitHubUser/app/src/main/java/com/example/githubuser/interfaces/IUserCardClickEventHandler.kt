package com.example.githubuser.interfaces

import android.widget.CheckBox
import androidx.cardview.widget.CardView
import com.example.githubuser.data.models.User

interface IUserCardClickEventHandler {

    fun onCardClickListener(card: CardView, user: User)

    fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User)

    fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User)

}