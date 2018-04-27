package edu.auburn.eng.csse.comp3710.spring2018.barminimum;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.android.aubiesays.R;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class MenuFragment extends Fragment {

    private MenuListener mCallback;

    public interface MenuListener {
        public void onStartSelected();
        public void onOptionsSelected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // set listener for start button
        Button.OnClickListener startListener = new Button.OnClickListener(){
            public void onClick(View v) {
                mCallback.onStartSelected();
            }
        };

        ((Button) getView().findViewById(R.id.start)).setOnClickListener(startListener);


        // set listener for options button
        Button.OnClickListener optionsListener = new Button.OnClickListener(){
            public void onClick(View v) {
                mCallback.onOptionsSelected();
            }
        };

        ((Button) getView().findViewById(R.id.options)).setOnClickListener(optionsListener);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (MenuListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MenuListener");
        }
    }
}
