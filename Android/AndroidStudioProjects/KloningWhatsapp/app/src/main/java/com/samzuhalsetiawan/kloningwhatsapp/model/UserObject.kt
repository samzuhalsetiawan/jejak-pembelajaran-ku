package com.samzuhalsetiawan.kloningwhatsapp.model

class UserObject() {
    var phoneName: String = ""
    var phoneNumber: String = ""

    constructor(phoneName: String, phoneNumber: String): this() {
        this.phoneName = phoneName
        this.phoneNumber = phoneNumber
    }

}