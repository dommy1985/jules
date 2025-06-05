# Tapjacking Demonstration Android App

**⚠️ FOR EDUCATIONAL AND SECURITY TESTING PURPOSES ONLY. DO NOT USE MALICIOUSLY. ⚠️**

This application is designed to demonstrate how a Tapjacking attack can be performed and mitigated in a controlled Android environment. It is intended for security researchers, developers, and students to understand this vulnerability.

## Purpose

Tapjacking is an attack where a malicious application displays a transparent or opaque overlay on top of a victim application. When the user interacts with the overlay (e.g., taps a button), they are unknowingly interacting with the underlying victim application. This can lead to unauthorized actions.

This app simulates:
1.  A **victim UI** (`MainActivity`) with sensitive buttons.
2.  A **transparent overlay** (`OverlayActivity`) that attempts to hijack taps.
3.  **Defenses** against Tapjacking.

## Features

*   **Target Interface:** A simple screen (`MainActivity`) with buttons simulating sensitive actions.
*   **Overlay Screen:** A semi-transparent overlay (`OverlayActivity`) that intercepts touch events.
*   **Click Feedback:** Toast messages to indicate where taps are registered (overlay or underlying buttons).
*   **XML-based Defense:** Demonstrates `android:filterTouchesWhenObscured="true"` on target Views.
*   **Kotlin-based Defense:** Demonstrates overriding `onFilterTouchEventForSecurity()` at the Activity level.

## How to Build and Run

1.  Clone this repository or import the project into Android Studio.
2.  Connect an Android emulator or a physical device (with USB debugging enabled).
3.  Select the `app` configuration and click the "Run" button in Android Studio.

## How to Test Tapjacking (Simulated Attack)

1.  Once the app is running, you'll see `MainActivity`.
2.  Click the **"Launch Overlay (Simulate Attack)"** button.
    *   This will launch `OverlayActivity`, which appears as a semi-transparent layer over `MainActivity`.
    *   You'll see text like "DECEPTIVE OVERLAY CONTENT" from the overlay.
3.  Try tapping on the area where the "Sensitive Action 1 (XML Defense)" or "Sensitive Action 2 (XML Defense)" buttons are visually positioned underneath the overlay.
4.  You should see a Toast message: **"Overlay Tapped! You thought you were tapping the screen below."**
    *   This confirms that the overlay intercepted your tap.
    *   Because of the defenses implemented, the underlying buttons should NOT register this tap.

## How to Observe Defenses

This demo includes two types of defenses:

### 1. XML Defense (`android:filterTouchesWhenObscured="true"`)

*   The "Sensitive Action 1 (XML Defense)" and "Sensitive Action 2 (XML Defense)" buttons in `MainActivity` have the `android:filterTouchesWhenObscured="true"` attribute set in `app/src/main/res/layout/activity_main.xml`.
*   **Test:**
    1.  Launch the overlay as described above.
    2.  Tap on the location of these "Sensitive Action..." buttons.
    3.  **Expected Behavior:**
        *   You will **only** see the "Overlay Tapped!" Toast.
        *   You will **NOT** see Toasts like "Sensitive Action 1 TAPPED!" from the underlying buttons. This is because `filterTouchesWhenObscured="true"` prevents them from receiving taps when an overlay (like our `OverlayActivity`) is present.

### 2. Kotlin Defense (Overriding `onFilterTouchEventForSecurity()`)

*   `MainActivity.kt` overrides the `onFilterTouchEventForSecurity()` method. This is an Activity-level defense.
*   **Test:**
    1.  Launch the overlay.
    2.  Tap anywhere on the screen that is part of `MainActivity` but now covered by the overlay.
    3.  **Expected Behavior:**
        *   You will see the "Overlay Tapped!" Toast (as the overlay always gets the event first).
        *   You should then see a Toast: **"Blocked obscured touch (Kotlin defense)!"**.
        *   Check Android Studio's Logcat (filter by "MainActivityTJ" or "Tapjacking"). You will see a warning: `"Blocked touch event because window is obscured (Tapjacking attempt detected)"`.
        *   This shows the entire `MainActivity` is preventing touches from passing through when it's obscured.

**Note on Defense Interaction:**
*   If a View has `android:filterTouchesWhenObscured="true"`, it handles the event first. If it blocks the touch, the Activity's `onFilterTouchEventForSecurity` might not even be called for that specific view's area, or its return value for that event might be superseded.
*   If a View has `android:filterTouchesWhenObscured="false"` (or it's not set, defaulting to false for many views), then the Activity's `onFilterTouchEventForSecurity` provides a fallback defense.

## Code Structure Highlights

*   `app/src/main/java/com/example/tapjackingdemo/`
    *   `MainActivity.kt`: Simulates the victim application's screen. Implements Kotlin-based defense.
    *   `OverlayActivity.kt`: Implements the deceptive overlay screen.
*   `app/src/main/res/layout/`
    *   `activity_main.xml`: Layout for `MainActivity`, including buttons with XML-based defense.
    *   `activity_overlay.xml`: Layout for `OverlayActivity`.
*   `app/src/main/AndroidManifest.xml`: Declares activities, sets themes (e.g., translucent theme for `OverlayActivity`), and includes educational comments.
*   `app/src/main/res/values/`: Contains strings, colors, and themes.

## ⚠️ Disclaimer

This application is strictly for educational and security testing purposes. Understanding how attacks like Tapjacking work is crucial for building more secure applications. **Do not use the techniques demonstrated here for any malicious activities.** Always respect privacy and ethical guidelines.
