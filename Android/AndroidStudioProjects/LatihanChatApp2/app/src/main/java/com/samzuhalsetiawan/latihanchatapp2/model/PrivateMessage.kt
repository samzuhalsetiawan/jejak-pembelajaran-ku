package com.samzuhalsetiawan.latihanchatapp2.model

class PrivateMessage() {

    var pesan = ""
    var senderUid = ""

    constructor(pesan: String, senderUid: String): this() {
        this.pesan = pesan
        this.senderUid = senderUid
    }

}