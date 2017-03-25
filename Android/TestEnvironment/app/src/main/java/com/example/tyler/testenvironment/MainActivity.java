package com.example.tyler.testenvironment;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        FragmentManager fm = getFragmentManager();
        SingleChoiceDialog dialog = new SingleChoiceDialog();
        dialog.show(fm, "tag");

    }
}
