package com.example.a3tabtest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // ImageView에 GIF 로드
        val imageView: ImageView = findViewById(R.id.splash_image)
        Glide.with(this)
            .asGif()
            .load(R.drawable.move_turtle)
            .into(imageView)

        // 타이머가 끝나면 내부 실행
        Handler().postDelayed({
            // 앱의 MainActivity로 넘어가기
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            // 현재 액티비티 닫기
            finish()
        }, 3000) // 3초
    }
}
