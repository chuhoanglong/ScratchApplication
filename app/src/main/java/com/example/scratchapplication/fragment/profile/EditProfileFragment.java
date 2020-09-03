package com.example.scratchapplication.fragment.profile;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.scratchapplication.R;

public class EditProfileFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_edit_profile);
    }
}
