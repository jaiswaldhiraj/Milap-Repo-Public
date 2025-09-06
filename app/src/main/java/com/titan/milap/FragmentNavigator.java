package com.titan.milap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.titan.milap.chatpage.ChatListFragment;
import com.titan.milap.homepage.HomeFragment;
import com.titan.milap.notificationpackage.NotificationFragment;
import com.titan.milap.profilepage.ProfileFragment;
import com.titan.milap.searchpage.SearchScreenFragment;

public class FragmentNavigator {

    public static boolean navigateTo(int itemId, FragmentManager fragmentManager, int containerId) {
        Fragment selectedFragment = null;
        String tag = null;

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
            tag = "home";
        } else if (itemId == R.id.nav_search) {
            selectedFragment = new SearchScreenFragment();
            tag = "search";
        }else if (itemId == R.id.nav_chat) {
            selectedFragment = new ChatListFragment();
            tag = "chat";
        }else if (itemId == R.id.nav_like) {
            selectedFragment = new NotificationFragment();
            tag = "notifications";
        }else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
            tag = "profile";
        }

        if (selectedFragment != null) {
            Fragment current = fragmentManager.findFragmentById(containerId);
            if (current != null && current.getClass().equals(selectedFragment.getClass())) {
                return true; // Already showing the selected fragment
            }

            if (selectedFragment instanceof HomeFragment) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // Clear back stack

                // Don't add to back stack
                fragmentManager.beginTransaction()
                        .replace(containerId, selectedFragment, tag)
                        .commit();
            } else {
                // Add to back stack
                fragmentManager.beginTransaction()
                        .replace(containerId, selectedFragment, tag)
                        .addToBackStack(tag)
                        .commit();
            }
            return true;
        }

        return false;
    }
}
