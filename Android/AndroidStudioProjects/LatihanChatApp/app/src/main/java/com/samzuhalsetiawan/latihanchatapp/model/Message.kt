package com.samzuhalsetiawan.latihanchatapp.model

class Message {
    var pesan = ""
    var senderUid = ""

    constructor () {}

    constructor (pesan: String, senderUid: String) {
        this.pesan = pesan
        this.senderUid = senderUid
    }
}