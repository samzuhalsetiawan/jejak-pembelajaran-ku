package com.samzuhalsetiawan.kloningwhatsapp.model

class Message() {
    var senderPhoneNumber = ""
    var message = ""
    constructor(senderPhoneNumber: String, message: String): this() {
        this.senderPhoneNumber = senderPhoneNumber
        this.message = message
    }
}