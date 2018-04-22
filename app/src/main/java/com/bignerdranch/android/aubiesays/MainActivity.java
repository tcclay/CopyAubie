package com.bignerdranch.android.aubiesays;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements MenuFragment.MenuListener, GameFragment.ScoreListener {

    private static final String TAG = "MainActivity";

    public static final String SCORE_KEY = "score";
    private static final String START_TRANSACTION = "startTransaction";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState == null) {
                Log.d(TAG, "Creating menu fragment");
                MenuFragment mainFragment = new MenuFragment();
                mainFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, mainFragment);
                transaction.commit();
            }
        }
    }

    public void onOptionsSelected() {
        Log.d(TAG, "Creating options fragment");
        OptionsFragment newFragment = new OptionsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit transaction
        transaction.commit();
    }

    public void onStartSelected() {
        Log.d(TAG, "Creating game fragment");
        GameFragment newFragment = new GameFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(START_TRANSACTION);

        // Commit transaction
        transaction.commit();
    }

    public void onShowScore(int score) {
        Log.d(TAG, "Creating Score fragment");
        ScoreFragment newFragment = new ScoreFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle args = new Bundle();
        args.putInt(SCORE_KEY, score);
        newFragment.setArguments(args);


        // I have no idea why this works but if you don't pop the backstack here
        // you will end up with multiple score fragments on the backstack
        // on repeat plays.
        manager.popBackStack();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit transaction
        transaction.commit();
    }
}