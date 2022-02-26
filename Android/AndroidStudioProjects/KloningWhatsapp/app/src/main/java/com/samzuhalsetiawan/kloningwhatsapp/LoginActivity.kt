package com.samzuhalsetiawan.kloningwhatsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.kloningwhatsapp.databinding.ActivityMainBinding
import com.samzuhalsetiawan.kloningwhatsapp.model.UserObject
import com.samzuhalsetiawan.kloningwhatsapp.utils.Iso
import com.samzuhalsetiawan.kloningwhatsapp.utils.UserPhoneContacts
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    companion object {
        var myPhoneNumber = ""
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val auth by lazy { Firebase.auth }
    private var mVerificationId: String? = null
    private val database by lazy { Firebase.database("https://kloning-whatsapp-d26a8-default-rtdb.asia-southeast1.firebasedatabase.app/") }
    private val dbRef by lazy { database.reference }
    private val telephonyManager by lazy { getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth.signOut()

        userIsLoggedIn()

        binding.btnSendVerificationCode.setOnClickListener {

            mVerificationId?.let {
                verifyPhoneNumberWithCode(it, binding.etVerificationCode.text.toString())
            } ?: startPhoneNumberVerification()

        }

    }

    private fun startPhoneNumberVerification() {

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
                myPhoneNumber = getInputPhoneNumber()
            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)

                mVerificationId = verificationId
                binding.btnSendVerificationCode.text = "Verify Code"

            }

        }

        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder()
                .setPhoneNumber(getInputPhoneNumber())
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callback)
                .build()
        )
    }

    private fun getInputPhoneNumber(): String {
        return binding.etPhoneNumber.text.toString().let { pNum ->
            val countryCode = Iso.getPhoneCountryCode(telephonyManager.networkCountryIso)
            when (pNum.first()) {
                '+' -> pNum
                '0' -> {
                    pNum.replaceFirstChar { countryCode }
                }
                else -> {
                    countryCode + pNum
                }
            }
        }.also { UserPhoneContacts.prunePhoneNumber(it) }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    userIsLoggedIn()
                }
            }
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    myPhoneNumber = getInputPhoneNumber()
                    userIsLoggedIn()
                }
            }
        }
    }

    private fun userIsLoggedIn() {
        val user = auth.currentUser
        user?.let {
            //TODO: dapatkan phoneNumber dari database bukan dari input
            addUserDataToDatabase(it.uid, getInputPhoneNumber())
        }
    }

    private fun addUserDataToDatabase(uid: String, phoneNumber: String) {
        dbRef.child("users").child(uid).setValue(UserObject("unknown", phoneNumber))
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        startActivity(Intent(this, MainPageActivity::class.java))
                        finish()
                    }
                }
            }
    }
}