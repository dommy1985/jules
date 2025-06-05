package com.example.tapjackingdemo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast

class OverlayActivity : Activity() { // Using android.app.Activity for Theme.Translucent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply a translucent theme. This should ideally be set in AndroidManifest.xml,
        // but can also be done programmatically before setContentView.
        // For this demo, we will ensure it's set in the Manifest as per plan.
        // setTheme(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) // Example if done programmatically

        setContentView(R.layout.activity_overlay)

        val overlayLayout: FrameLayout = findViewById(R.id.overlay_layout)

        overlayLayout.setOnClickListener {
            // This Toast confirms that the overlay was tapped.
            // In a real attack, this click would be passed through (or not)
            // to the underlying view deceitfully.
            Toast.makeText(this, "Overlay Tapped! You thought you were tapping the screen below.", Toast.LENGTH_LONG).show()

            // Educational comment:
            // This is the OverlayActivity. It's designed to sit on top of other apps.
            // Its transparency is key to the Tapjacking attack.
            // The click listener here intercepts touches meant for the UI underneath.
            // THIS APP IS FOR EDUCATIONAL PURPOSES ONLY. DO NOT USE MALICIOUSLY.
        }

        // Further educational comment
        // For the purpose of this demo, the overlay is made slightly visible.
        // In a real attack, it might be completely transparent or cleverly disguised.
    }
}
