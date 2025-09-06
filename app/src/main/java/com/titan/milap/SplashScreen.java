package com.titan.milap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.titan.milap.homepage.HomeActivity;
import com.titan.milap.loginsetup.AddPhotosLoginTime;
import com.titan.milap.loginsetup.DistancePreference;
import com.titan.milap.loginsetup.GenderPage;
import com.titan.milap.loginsetup.GetLocation;
import com.titan.milap.loginsetup.InterestedPerson;
import com.titan.milap.loginsetup.LoginMoreAboutGender;
import com.titan.milap.loginsetup.LoginPhoneNumber;
import com.titan.milap.loginsetup.RelationType;
import com.titan.milap.loginsetup.UserAboutPage;
import com.titan.milap.loginsetup.UserBio;
import com.titan.milap.loginsetup.UserHobbyInterest;
import com.titan.milap.loginsetup.UserId;
import com.titan.milap.loginsetup.UserReligion;
import com.titan.milap.loginsetup.UserStatus;

/**
 * Splash screen that displays for a few seconds when the app launches.
 * After delay, it checks login status and routes user to the correct screen.
 */
@SuppressLint("CustomSplashScreen") // Used to suppress warning if no custom splash theme is used
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enables full edge-to-edge layout
        EdgeToEdge.enable(this);

        // Set splash screen layout
        setContentView(R.layout.activity_splash_screen);

        // Handle insets for notches/status bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Force light mode (disables dark theme)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Wait 4 seconds then decide where to go next
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences pref = getSharedPreferences("log_details", MODE_PRIVATE);
            boolean is_logged_in = pref.getBoolean("is_logged_in", false);

            SharedPreferences setupPref = getSharedPreferences("setup_details", MODE_PRIVATE);
            boolean is_setup_complete = setupPref.getBoolean("is_setup_complete", false);


            SharedPreferences setupPrefScreen = getSharedPreferences("setup_details", MODE_PRIVATE);
            String currentScreen = setupPrefScreen.getString("current_setup_screen", "UserId");

            Intent iNext = getINext(is_logged_in, is_setup_complete,currentScreen);

            startActivity(iNext);
            finish(); // close SplashScreen so user can't return with back button
        }, 2000); // 2000ms = 3 seconds delay
    }

    @NonNull
    private Intent getINext(boolean is_logged_in, boolean is_setup_complete,String currentScreen) {
        Intent iNext;

        if (!is_logged_in) {
            // Case 1: User is not logged in at all
            iNext = new Intent(SplashScreen.this, LoginPhoneNumber.class);
        } else if (!is_setup_complete) {
            // Case 2: Logged in, but setup not complete - continue from where they left

            switch (currentScreen) {
                case "UserAboutPage":
                    iNext = new Intent(SplashScreen.this, UserAboutPage.class);
                    break;

                case "GenderPage":
                    iNext = new Intent(SplashScreen.this, GenderPage.class);
                    break;

                case "LoginMoreAboutGender":
                    iNext = new Intent(SplashScreen.this, LoginMoreAboutGender.class);
                    break;

                case "InterestedPerson":
                    iNext = new Intent(SplashScreen.this, InterestedPerson.class);
                    break;

                case "UserStatus":
                    iNext = new Intent(SplashScreen.this, UserStatus.class);
                    break;

                case "UserReligion":
                    iNext = new Intent(SplashScreen.this, UserReligion.class);
                    break;

                case "RelationType":
                    iNext = new Intent(SplashScreen.this, RelationType.class);
                    break;

                case "UserHobbyInterest":
                    iNext = new Intent(SplashScreen.this, UserHobbyInterest.class);
                    break;

                case "GetLocation":
                    iNext = new Intent(SplashScreen.this, GetLocation.class);
                    break;

                case "DistancePreference":
                    iNext = new Intent(SplashScreen.this, DistancePreference.class);
                    break;

                case "UserBio":
                    iNext = new Intent(SplashScreen.this, UserBio.class);
                    break;

                case "AddPhotosLoginTime":
                    iNext = new Intent(SplashScreen.this, AddPhotosLoginTime.class);
                    break;

                default:
                    iNext = new Intent(SplashScreen.this, UserId.class);
            }
        } else {
            // Case 3: All done, go to home
            iNext = new Intent(SplashScreen.this, HomeActivity.class);

        }

        return iNext;
    }
}
