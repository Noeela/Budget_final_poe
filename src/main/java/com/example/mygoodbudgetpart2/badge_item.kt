package com.example.mygoodbudgetpart2

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class badge_item : AppCompatActivity() {
    private lateinit var badgeIcon: ImageView
    private lateinit var textBadgeTitle: TextView
    private lateinit var textBadgeDescription: TextView
    private lateinit var btnBackHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge_item)

        // Initialize views
        badgeIcon = findViewById(R.id.badgeIcon)
        textBadgeTitle = findViewById(R.id.textBadgeTitle)
        textBadgeDescription = findViewById(R.id.textBadgeDescription)
        btnBackHome = findViewById(R.id.btnBackHome)

        // Example: Set badge data (you can replace this with data from DB or intent)
        badgeIcon.setImageResource(R.drawable.ic_badge_locked)  // Change icon as needed
        textBadgeTitle.text = "First Goal Achiever"
        textBadgeDescription.text = "Awarded for setting your first budget goal."

        // Button click to go back to home (MainActivity)
        btnBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}