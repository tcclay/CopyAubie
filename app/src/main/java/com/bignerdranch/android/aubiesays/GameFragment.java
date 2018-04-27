package com.bignerdranch.android.aubiesays;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class GameFragment extends Fragment implements MediaPlayer.OnPreparedListener {

    // constants
    private static final int[] BUTTON_IDS = {R.id.blue, R.id.red, R.id.green, R.id.yellow};
    private static final double ACTIVATE_PERCENT = 0.8;
    private static final String TAG = "GameFragment";
    private static final int MAX_HINTS = 3;
    private static final String DEFAULT_SPEED = "800";

    // state tags
    private static final String SEQUENCE_TAG = "sequence";
    private static final String INDEX_TAG = "index";
    private static final String SEQUENCE_INDEX_TAG = "sequence_index";
    private static final String HINTS_TAG = "hints";

    // state member variables
    private Sequence mSequence;
    private int mCurrentIndex;
    private int mSequenceIndex;
    private int mNumHints;

    // non-state member variables
    private Handler mHandler;
    private ScoreListener mCallback;
    private final MediaPlayer mBlueSound = new MediaPlayer();
    private final MediaPlayer mRedSound = new MediaPlayer();
    private final MediaPlayer mGreenSound = new MediaPlayer();
    private final MediaPlayer mYellowSound = new MediaPlayer();

    public interface ScoreListener {
        public void onShowScore(int score);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // restore variables from Bundle
            mSequence = savedInstanceState.getParcelable(SEQUENCE_TAG);
            mCurrentIndex = savedInstanceState.getInt(INDEX_TAG);
            mSequenceIndex = savedInstanceState.getInt(SEQUENCE_INDEX_TAG);
            mNumHints = savedInstanceState.getInt(HINTS_TAG);
        }
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mSequence == null) {
            mSequence = new Sequence(BUTTON_IDS);
            mCurrentIndex = 0;
            mSequenceIndex = 0;
            mNumHints = MAX_HINTS;
        }
        return inflater.inflate(R.layout.game, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ScoreListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScoreListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (int buttonId : BUTTON_IDS) {
            try {
                setSoundPlayer(buttonId);
            }
            catch (IOException e) {
                Log.d(TAG, "Error reading sound file");
            }
        }

        initializeButtons();

        playSequence();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SEQUENCE_TAG, mSequence);
        outState.putInt(INDEX_TAG, mCurrentIndex);
        outState.putInt(SEQUENCE_INDEX_TAG, mSequenceIndex);
        outState.putInt(HINTS_TAG, mNumHints);
    }

    @Override
    public void onStop() {
        super.onStop();
        for (int i : BUTTON_IDS) {
            Button b = getView().findViewById((int) i);
            b.getHandler().removeCallbacksAndMessages(null);
        }
        mHandler.removeCallbacksAndMessages(null);

        mBlueSound.release();
        mRedSound.release();
        mGreenSound.release();
        mYellowSound.release();
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    public void setSoundPlayer(int buttonId) throws IOException {
        AssetFileDescriptor soundFile;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean enableSound = sharedPrefs.getBoolean("prefSound", true);

        switch (buttonId) {
            case R.id.blue:
                soundFile = getContext().getAssets().openFd("E4.wav");
                mBlueSound.setDataSource(soundFile.getFileDescriptor(), soundFile.getStartOffset(), soundFile.getDeclaredLength());
                mBlueSound.setOnPreparedListener(this);
                break;
            case R.id.red:
                soundFile = getContext().getAssets().openFd("A4.wav");
                mRedSound.setDataSource(soundFile.getFileDescriptor(), soundFile.getStartOffset(), soundFile.getDeclaredLength());
                mRedSound.setOnPreparedListener(this);
                break;
            case R.id.green:
                soundFile = getContext().getAssets().openFd("E3.wav");
                mGreenSound.setDataSource(soundFile.getFileDescriptor(), soundFile.getStartOffset(), soundFile.getDeclaredLength());
                mGreenSound.setOnPreparedListener(this);
                break;
            case R.id.yellow:
                soundFile = getContext().getAssets().openFd("C4#.wav");
                mYellowSound.setDataSource(soundFile.getFileDescriptor(), soundFile.getStartOffset(), soundFile.getDeclaredLength());
                mYellowSound.setOnPreparedListener(this);
                break;
            default:
                soundFile = getContext().getAssets().openFd("C4#.wav");
                break;
        }


        if (!enableSound) {
            mBlueSound.setVolume(0, 0);
            mRedSound.setVolume(0, 0);
            mGreenSound.setVolume(0, 0);
            mYellowSound.setVolume(0, 0);
        }
    }

    public void activateButton(int buttonId) throws IOException {
        /* TODO implement this
         * We need to flash the button/show aubie on the button and
         * play the sound for a period shorter than the difficulty delay
         */

        /* TODO change later
         * for now just making activated button black
         */

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int speed = Integer.parseInt(sharedPrefs.getString("prefDifficulty", DEFAULT_SPEED));
        final Button b;
        final Drawable d;

        switch (buttonId) {
            case R.id.blue:
                mBlueSound.prepareAsync();
                mBlueSound.setLooping(true);

                b = (Button) getView().findViewById(buttonId);
                b.setBackground(getResources().getDrawable(R.drawable.upper_right_button_active, getContext().getTheme()));
                b.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.setBackground(getResources().getDrawable(R.drawable.upper_right_button, getContext().getTheme()));
                        mBlueSound.stop();
                        mSequenceIndex++;
                    }
                }, (long) (speed * ACTIVATE_PERCENT));
                break;
            case R.id.red:
                mRedSound.prepareAsync();
                mRedSound.setLooping(true);

                b = (Button) getView().findViewById(buttonId);
                b.setBackground(getResources().getDrawable(R.drawable.upper_left_button_active, getContext().getTheme()));
                b.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.setBackground(getResources().getDrawable(R.drawable.upper_left_button, getContext().getTheme()));
                        mRedSound.stop();
                        mSequenceIndex++;
                    }
                }, (long) (speed * ACTIVATE_PERCENT));
                break;
            case R.id.green:
                mGreenSound.prepareAsync();
                mGreenSound.setLooping(true);

                b = (Button) getView().findViewById(buttonId);
                b.setBackground(getResources().getDrawable(R.drawable.lower_left_button_active, getContext().getTheme()));
                b.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.setBackground(getResources().getDrawable(R.drawable.lower_left_button, getContext().getTheme()));
                        mGreenSound.stop();
                        mSequenceIndex++;
                    }
                }, (long) (speed * ACTIVATE_PERCENT));
                break;
            case R.id.yellow:
                mYellowSound.prepareAsync();
                mYellowSound.setLooping(true);

                b = (Button) getView().findViewById(buttonId);
                b.setBackground(getResources().getDrawable(R.drawable.lower_right_button_active, getContext().getTheme()));
                b.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.setBackground(getResources().getDrawable(R.drawable.lower_right_button, getContext().getTheme()));
                        mYellowSound.stop();
                        mSequenceIndex++;
                    }
                }, (long) (speed * ACTIVATE_PERCENT));
                break;
            default:
                break;
        }
    }

    public void setButtonsEnabled(boolean enable) {
        /*
         * We need to set the OnClickListener for each button
         * to be null.
         */
        for (int i : BUTTON_IDS) {
            ((Button) getView().findViewById(i)).setEnabled(enable);
        }

        Button b = (Button) getView().findViewById(R.id.hint);
        if (enable) {
            b.setVisibility(View.VISIBLE);
            b.setEnabled(true);
        }
        else {
            b.setVisibility(View.GONE);
            b.setEnabled(false);
        }
    }

    public void initializeButtons() {
        /* TODO implement this
         * Each OnClickListener needs to verify if the button is correct and
         * then increment the current index. Finally, if the pressed button
         * is the last one in the sequence, we need to show the user that
         * they entered a sequence correctly, extend the sequence, then play
         * the sequence.
         */

        Button.OnTouchListener buttonListener = new Button.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent e) {
                Log.d(TAG, "View ID: " + v.getId());
                Log.d(TAG, "Current Index: " + mCurrentIndex);
                Log.d(TAG, "Verify: " + mSequence.verify(v.getId(), mCurrentIndex));
                int theId = v.getId();
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    if (mSequence.verify(theId, mCurrentIndex)) {
                        mCurrentIndex++;

                        switch (v.getId()) {
                            case R.id.blue:
                                mBlueSound.stop();
                                v.setBackground(getResources().getDrawable(R.drawable.upper_right_button, getContext().getTheme()));
                                break;
                            case R.id.red:
                                mRedSound.stop();
                                v.setBackground(getResources().getDrawable(R.drawable.upper_left_button, getContext().getTheme()));
                                break;
                            case R.id.green:
                                mGreenSound.stop();
                                v.setBackground(getResources().getDrawable(R.drawable.lower_left_button, getContext().getTheme()));
                                break;
                            case R.id.yellow:
                                mYellowSound.stop();
                                v.setBackground(getResources().getDrawable(R.drawable.lower_right_button, getContext().getTheme()));
                                break;
                        }

                        if (mCurrentIndex == mSequence.getSize()) {
                            setButtonsEnabled(false);
                            mCurrentIndex = 0;
                            mSequenceIndex = 0;
                            mSequence.extend();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    playSequence();
                                }
                            }, 300);
                        }
                    } else {
                        mCallback.onShowScore(mSequence.getSize() - 1);
                    }
                    Log.d(TAG, "Action up");
                    return false;
                }
                else if (e.getAction() == MotionEvent.ACTION_DOWN){

                    switch (v.getId()) {
                        case R.id.blue:
                            mBlueSound.prepareAsync();
                            v.setBackground(getResources().getDrawable(R.drawable.upper_right_button_active, getContext().getTheme()));
                            break;
                        case R.id.red:
                            mRedSound.prepareAsync();
                            v.setBackground(getResources().getDrawable(R.drawable.upper_left_button_active, getContext().getTheme()));
                            break;
                        case R.id.green:
                            mGreenSound.prepareAsync();
                            v.setBackground(getResources().getDrawable(R.drawable.lower_left_button_active, getContext().getTheme()));
                            break;
                        case R.id.yellow:
                            mYellowSound.prepareAsync();
                            v.setBackground(getResources().getDrawable(R.drawable.lower_right_button_active, getContext().getTheme()));
                            break;
                    }
                    Log.d(TAG, "Action down");
                    return true;
                }

                return true;
            }
        };

        for (int i : BUTTON_IDS) {
            //Log.d(TAG, "Button ID: " + i);
            ((Button) getView().findViewById(i)).setOnTouchListener(buttonListener);
            ((Button) getView().findViewById(i)).setEnabled(true);
        }

        Button.OnClickListener hintListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSequenceIndex = 0;
                mNumHints--;
                if (mNumHints <= 0) {
                    v.setEnabled(false);
                    v.setVisibility(View.GONE);
                }
                playSequence();
                String text = "Hints: " + mNumHints;
                ((Button) v).setText(text);
            }
        };
        Button b = (Button) getView().findViewById(R.id.hint);
        b.setOnClickListener(hintListener);
        String text = "Hints: " + mNumHints;
        b.setText(text);
    }

    public void playSequence() {
        setButtonsEnabled(false);
        ArrayList<Integer> buttons = mSequence.getSequence();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int speed = Integer.parseInt(sharedPrefs.getString("prefDifficulty", DEFAULT_SPEED));


        int index = 0;
        for (int j = mSequenceIndex; j < buttons.size(); j++) {
            int i = (int) buttons.get(j);
            Button b = getView().findViewById(i);
            final int id = i;
            final int sequenceIndex = index;
            b.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Showing button " + sequenceIndex + " in sequence.");
                    try {
                        activateButton(id);
                    }
                    catch (IOException e) {
                        Log.d(TAG, "Error reading sound file");
                    }
                }
            }, index * speed);

            index++;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Enabling buttons now");
                setButtonsEnabled(true);
            }
        }, index * speed);

    }
}
