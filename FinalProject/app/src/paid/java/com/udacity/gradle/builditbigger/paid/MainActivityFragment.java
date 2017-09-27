package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.riskitbiskit.displayactivity.DisplayActivity;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeCallback{
    TextView instructionsTextView;
    ProgressBar mProgressBar;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        instructionsTextView = (TextView) root.findViewById(R.id.instructions_text_view);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        Button tellJokeButton = (Button) root.findViewById(R.id.joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask(MainActivityFragment.this).execute(getContext());
            }
        });

        return root;
    }

    @Override
    public void jokeCallback(String joke) {
        mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), DisplayActivity.class);
        intent.putExtra("jokeTag", joke);
        startActivity(intent);
    }
}
