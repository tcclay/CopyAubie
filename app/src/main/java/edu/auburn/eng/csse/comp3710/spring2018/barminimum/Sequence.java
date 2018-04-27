package edu.auburn.eng.csse.comp3710.spring2018.barminimum;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class Sequence implements Parcelable {

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

    // Parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeIntArray(mButtonIds);
        out.writeList(mCurrentSequence);
    }

    public static final Parcelable.Creator<Sequence> CREATOR
            = new Parcelable.Creator<Sequence>() {
        @Override
        public Sequence createFromParcel(Parcel in) {
            return new Sequence(in);
        }

        @Override
        public Sequence[] newArray(int size) {
            return new Sequence[size];
        }
    };

    private Sequence(Parcel in) {
        in.readIntArray(mButtonIds);
        mCurrentSequence = in.readArrayList(Integer.class.getClassLoader());
    }
}