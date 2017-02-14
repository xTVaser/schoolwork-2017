package com.example.tyler.evenodd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class evenodd extends AppCompatActivity {

    private TextView question;
    private TextView points;
    private Button even;
    private Button odd;
    private Button start;
    private Button clear;

    private EditText input;


    private int currentNumber;
    private int pointsCorrect;
    private int pointsIncorrect;

    private int numTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenodd);

        question = (TextView)findViewById(R.id.question);
        points = (TextView)findViewById(R.id.answer);
        even = (Button)findViewById(R.id.even);
        odd = (Button)findViewById(R.id.odd);

        start = (Button)findViewById(R.id.start);
        clear = (Button)findViewById(R.id.clear);

        input = (EditText)findViewById(R.id.input);

        currentNumber = (int)(Math.random()*100) + 1;

        question.setText("");
        points.setText("0");

    }


    public void evenPressed(View view) {

        if (currentNumber % 2 == 0) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            pointsCorrect++;
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            pointsIncorrect++;
        }

        if (numTests <= 0)
            points.setText("You did " + pointsCorrect + " right and " + pointsIncorrect + " wrong!");
        else
            update();
    }

    public void oddPressed(View view) {

        if (currentNumber % 2 != 0) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            pointsCorrect++;
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            pointsIncorrect++;
        }
        if (numTests <= 0)
            points.setText("You did " + pointsCorrect + " right and " + pointsIncorrect + " wrong!");
        else
            update();
    }

    public void update() {

        currentNumber = (int)(Math.random()*100) + 1;
        question.setText("Is " + currentNumber + " even or odd?");
        points.setText("0");
        numTests--;
    }

    public void clear(View view) {

        pointsCorrect = 0;
        pointsIncorrect = 0;

        question.setText("");
        points.setText("0");

    }

    public void beginTest(View view) {


        numTests = Integer.parseInt(input.getText().toString());
        update();


    }
}
