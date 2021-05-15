package com.stake.hencoder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TwoPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_pager)
    }

    fun viewClick(view: View) {
        Toast.makeText(this, "view click", Toast.LENGTH_SHORT).show()
    }
}