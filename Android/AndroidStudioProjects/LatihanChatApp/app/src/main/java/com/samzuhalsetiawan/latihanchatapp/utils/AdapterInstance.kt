package com.samzuhalsetiawan.latihanchatapp.utils

import android.content.Context
import com.samzuhalsetiawan.latihanchatapp.adapter.PrivateMessageAdapter
import com.samzuhalsetiawan.latihanchatapp.adapter.UserAdapter
import com.samzuhalsetiawan.latihanchatapp.data.AppData
import com.samzuhalsetiawan.latihanchatapp.data.AppData.listUser

object AdapterInstance {
    val listUserAdapter = UserAdapter(listUser)
    val messageAdapter = PrivateMessageAdapter(AppData.listMessage)
}