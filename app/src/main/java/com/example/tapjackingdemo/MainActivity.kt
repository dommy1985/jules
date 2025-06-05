package com.example.tapjackingdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log // Added for logging
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTJ" // Tag for logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensitiveButton1: Button = findViewById(R.id.sensitive_button1)
        val sensitiveButton2: Button = findViewById(R.id.sensitive_button2)
        val launchOverlayButton: Button = findViewById(R.id.launch_overlay_button)

        sensitiveButton1.setOnClickListener {
            Toast.makeText(this, "Sensitive Action 1 TAPPED!", Toast.LENGTH_SHORT).show()
        }

        sensitiveButton2.setOnClickListener {
            Toast.makeText(this, "Sensitive Action 2 TAPPED!", Toast.LENGTH_SHORT).show()
        }

        launchOverlayButton.setOnClickListener {
            val intent = Intent(this, OverlayActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Launching deceptive overlay...", Toast.LENGTH_SHORT).show()
        }

        // Educational comment
        // This MainActivity represents a normal application screen that could be targeted by a Tapjacking attack.
    }

    /**
     * Kotlin-based Tapjacking Defense:
     * Overriding onFilterTouchEventForSecurity to filter touch events when the window is obscured.
     *
     * This method is called when a touch event is received by the window.
     * @param event The motion event.
     * @return Return true if the event should be filtered (dropped), false otherwise.
     */
    override fun onFilterTouchEventForSecurity(event: MotionEvent): Boolean {
        // Check if the window is obscured by another window.
        // The FLAG_WINDOW_IS_OBSCURED bit is set if the window is obscured by another visible window.
        // The FLAG_WINDOW_IS_PARTIALLY_OBSCURED bit is set if the window is partially obscured by another visible window.
        // For robust defense, one might check for either. Here we check for fully obscured.
        if ((event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED) != 0) {
            // Window is obscured, event should be filtered.
            // Log this attempt for security auditing/awareness.
            Log.w(TAG, "Blocked touch event because window is obscured (Tapjacking attempt detected). Flags: ${event.flags}")
            Toast.makeText(this, "Blocked obscured touch (Kotlin defense)!", Toast.LENGTH_SHORT).show()

            // Educational comment:
            // THIS IS WHERE THE TOUCH IS BLOCKED due to the window being obscured.
            // This is a programmatic defense against Tapjacking.
            // It's important to note that `android:filterTouchesWhenObscured="true"` on a View
            // provides a simpler way to achieve this for individual views. This Activity-level
            // override can serve as a more general defense or for more complex logic.
            // THIS APP IS FOR EDUCATIONAL PURPOSES ONLY.
            return true // True means filter (block) the event.
        }
        return super.onFilterTouchEventForSecurity(event) // Default behavior: don't filter.
    }
}
