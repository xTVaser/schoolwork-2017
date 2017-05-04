package com.example.tyler.showmark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMark extends AppCompatActivity {

    TextView outputName;
    TextView outputMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_mark);

        outputName = (TextView) findViewById(R.id.output_name);
        outputMark = (TextView) findViewById(R.id.output_mark);

        Intent savedData = getIntent();

        outputName.setText("Hello " + savedData.getStringExtra("name"));
        outputMark.setText("Your midterm exam mark is " + savedData.getStringExtra("mark"));

    }
}
