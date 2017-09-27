package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.riskitbiskit.displayactivity.DisplayActivity;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeCallback {

    TextView instructionsTextView;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;


    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        instructionsTextView = (TextView) root.findViewById(R.id.instructions_text_view);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mProgressBar.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask(MainActivityFragment.this).execute(getContext());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                new EndpointsAsyncTask(MainActivityFragment.this).execute(getContext());
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Button tellJokeButton = (Button) root.findViewById(R.id.joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    new EndpointsAsyncTask(MainActivityFragment.this).execute(getContext());
                }
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
