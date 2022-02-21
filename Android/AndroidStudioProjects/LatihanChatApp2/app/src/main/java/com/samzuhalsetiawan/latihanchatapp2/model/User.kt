package com.samzuhalsetiawan.latihanchatapp2.model

class User() {
    var username = ""
    var email = ""
    var uid = ""

    constructor(username: String, email: String, uid: String): this() {
        this.username = username
        this.email = email
        this.uid = uid
    }

}