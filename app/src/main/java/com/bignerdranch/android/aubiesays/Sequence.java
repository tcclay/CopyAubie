package com.bignerdranch.android.aubiesays;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class Sequence {

    private int[] mButtonIds;
    private ArrayList<Integer> mCurrentSequence;

    public Sequence(int[] ids) {
        mButtonIds = ids;
        mCurrentSequence = new ArrayList<Integer>();
        extend();
    }

    public void extend() {
        Random rand = new Random();
        mCurrentSequence.add((Integer) mButtonIds[Math.abs(rand.nextInt()) % mButtonIds.length]);
    }

    public boolean verify(int buttonId, int index) {
        return buttonId == (int) mCurrentSequence.get(index);
    }

    public int getSize() {
        return mCurrentSequence.size();
    }

    public ArrayList<Integer> getSequence() {
        return mCurrentSequence;
    }
}