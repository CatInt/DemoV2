package com.aeron.demov1;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game2048Fragment extends android.support.v4.app.Fragment {

    public Game2048Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_game2048, container, false);
        final com.aeron.game2048.GameGrid gameGrid = (com.aeron.game2048.GameGrid) rootView.findViewById(R.id.gameView);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_game);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_anim));
                gameGrid.onGameStart();
            }
        });
        return rootView;

    }


}
