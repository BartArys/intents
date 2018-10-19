package com.example.eothein.intentexample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.eothein.intentexample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    companion object {

        /**
         * Generates an intent to create a [OtherActivity]
         */
        fun newFullScreenIntent(context: Context): Intent {
            val intent = Intent(context, OtherActivity::class.java)
            return intent
        }
    }

}
