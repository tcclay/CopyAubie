package com.bignerdranch.android.aubiesays;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class ScoreFragment extends Fragment {

    private int score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        score = args.getInt(MainActivity.SCORE_KEY);
        return inflater.inflate(R.layout.end, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        String text = "Score: " + score;
        ((TextView) getView().findViewById(R.id.your_score)).setText(text);
    }
}