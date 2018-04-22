package com.bignerdranch.android.aubiesays;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class OptionsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
