package com.example.android.jokedisplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment containing Joke displaying activity
 */
public class JokeActivityFragment extends Fragment {

    private String mJoke;

    private TextView mJoke_tv;

    public JokeActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity().getIntent()!= null){
            mJoke = getActivity().getIntent().getStringExtra(JokeActivity.jokeName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);

        mJoke_tv = root.findViewById(R.id.tv_joke);

        mJoke_tv.setText(mJoke);

        return root;
    }
}
