package com.bignerdranch.android.aubiesays;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lawrence-S on 4/21/2018.
 */

public class MenuFragment extends Fragment {
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
                GameFragment newFragment = new GameFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                // Commit transaction
                transaction.commit();
            }
        };

        ((Button) getView().findViewById(R.id.start)).setOnClickListener(startListener);


        // set listener for options button
        Button.OnClickListener optionsListener = new Button.OnClickListener(){
            public void onClick(View v) {
                OptionsFragment newFragment = new OptionsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                // Commit transaction
                transaction.commit();
            }
        };

        ((Button) getView().findViewById(R.id.options)).setOnClickListener(optionsListener);
    }
}
