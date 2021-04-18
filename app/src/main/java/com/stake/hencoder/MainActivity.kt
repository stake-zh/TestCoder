package com.stake.hencoder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stake.hencoder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    fun customView(view: View) {
        startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
    }

    fun materialEditText(view: View) {
        startActivity(Intent(this@MainActivity, MaterialEditTextActivity::class.java))
    }
}