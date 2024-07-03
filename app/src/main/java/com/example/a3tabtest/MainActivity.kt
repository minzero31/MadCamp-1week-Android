// MainActivity.kt
package com.example.a3tabtest

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // 커스텀 뷰를 액션바에 추가
        val actionBar = supportActionBar
        if (actionBar != null) {
            val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val customView: View = inflater.inflate(R.layout.actionbar_custom_view, null)

            val imageView: ImageView = customView.findViewById(R.id.actionbar_image_view)
            // Glide를 사용하여 GIF 로드
            Glide.with(this)
                .asGif()
                .load(R.drawable.move_turtle) // 실제 GIF 파일로 대체하세요
                .into(imageView)
            val titleTextView: TextView = customView.findViewById(R.id.actionbar_title)
            // 커스텀 폰트를 적용
            val typeface: Typeface = resources.getFont(R.font.pop)
            titleTextView.typeface = typeface
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.customView = customView
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}