package com.example.eothein.intentexample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.eothein.intentexample.R

class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
    }

    companion object {

        /**
         * Generates an intent to create a [OtherActivity]
         * This seems unnecessary as there are no extra's to be added to the intent,
         * but it's good practice to always do this.
         */
        fun otherActivityIntent(context: Context): Intent {
            return Intent(context, OtherActivity::class.java)
        }
    }
}
