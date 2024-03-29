package com.example.courier.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.courier.MainActivity
import com.example.courier.R
import com.example.courier.service.Rabbit
import com.example.courier.enums.RabbitCode
import com.example.courier.models.GetSettings
import com.google.zxing.integration.android.IntentIntegrator

class SettingActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        findViewById<Button>(R.id.back).setOnClickListener {
            startActivity(Intent(this@SettingActivity, HomeActivity::class.java))
            finish()
        }
        findViewById<TextView>(R.id.scanner_setting).setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Отсканируйте QR-код у администратора")
            integrator.initiateScan()
        }
        findViewById<TextView>(R.id.logout).setOnClickListener {
            val token = GetSettings(this).load("token")
            Rabbit(this).sendMessage(token, RabbitCode.LOGOUT, "ok")
            GetSettings(this).remove("token")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}