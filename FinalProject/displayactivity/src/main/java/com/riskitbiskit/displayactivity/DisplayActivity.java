package com.riskitbiskit.displayactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(intent.getStringExtra("jokeTag"));
    }
}
