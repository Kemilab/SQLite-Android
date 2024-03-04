package com.example.androidtest

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        val name: String? = intent.getStringExtra("name")
        val message = getString(R.string.good_job_you_made_10_steps, name)
        val textShare = findViewById<TextView>(R.id.textShare)
        textShare.text = message
    }
    fun setOnClickListenerShare(view: View) {

        val radioGroup = findViewById<RadioGroup>(R.id.phones)

        val selectedId = radioGroup.checkedRadioButtonId


        if (selectedId == View.NO_ID) {

            Toast.makeText(this, "Please select a phone number", Toast.LENGTH_SHORT).show()
        } else {
            val selectedButton = findViewById<RadioButton>(selectedId)

            val selectedText = selectedButton.text.toString() //
            val name: String? = intent.getStringExtra("name")
            val smsNumber: String = selectedText
            val smsText: String = "Good job $name! You made 10 steps"
            val uri = Uri.parse("smsto:$smsNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", smsText)
            startActivity(intent)
        }

    }


}