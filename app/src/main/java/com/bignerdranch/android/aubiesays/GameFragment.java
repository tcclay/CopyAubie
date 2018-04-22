package com.bignerdranch.android.aubiesays;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class GameFragment extends Fragment {

    private static final int[] BUTTON_IDS = {R.id.blue, R.id.red, R.id.green, R.id.yellow};

    private Sequence mSequence;
    private int mCurrentIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // restore variables from Bundle
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mSequence == null) {
            mSequence = new Sequence(BUTTON_IDS);
            mCurrentIndex = 0;
        }
        return inflater.inflate(R.layout.game, container, false);
    }

    public void activateButton(int buttonId) {
        /* TODO implement this
         * We need to flash the button/show aubie on the button and
         * play the sound for a period shorter than the difficulty delay
         */
    }

    public void disableButtons() {
        /* TODO implement this
         * We need to set the OnClickListener for each button
         * to be null.
         */
    }

    public void enableButtons() {
        /* TODO implement this
         * Each OnClickListener needs to verify if the button is correct and
         * then increment the current index. Finally, if the pressed button
         * is the last one in the sequence, we need to show the user that
         * they entered a sequence correctly, extend the sequence, then play
         * the sequence.
         */
    }

    public void playSequence() {
        disableButtons();
        ArrayList<Integer> buttons = mSequence.getSequence();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int speed = Integer.parseInt(sharedPrefs.getString("prefDifficulty", "NULL"));


        int index = 0;
        for (Integer i : buttons) {
            Button b = getView().findViewById((int) i);
            final int id = i;
            b.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activateButton(id);
                }
            }, index * speed);

            index++;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableButtons();
                }
            }, index * speed);
        }
    }


}
