package com.example.tyler.labquesiton2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CustomView shapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shapeView = (CustomView) findViewById(R.id.shapes);
    }

    public void updateShape(int item) {


        shapeView.shapeType = item;
        shapeView.postInvalidate();
    }
}
