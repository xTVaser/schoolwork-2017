package com.example.tyler.colorpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ColorPicker extends AppCompatActivity {

    private int greenCounter = 0;
    private int goldCounter = 0;
    private int maroonCounter = 0;
    private int purpleCounter = 0;

    TextView greenText;
    TextView goldText;
    TextView maroonText;
    TextView purpleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        greenText = (TextView) findViewById(R.id.green);
        goldText = (TextView) findViewById(R.id.gold);
        maroonText = (TextView) findViewById(R.id.maroon);
        purpleText = (TextView) findViewById(R.id.purple);
    }

    public void incGreen(View view) {
        greenCounter++;
        updateTextFields(view);
    }

    public void incGold(View view) {
        goldCounter++;
        updateTextFields(view);
    }

    public void incMaroon(View view) {
        maroonCounter++;
        updateTextFields(view);
    }

    public void incPurple(View view) {
        purpleCounter++;
        updateTextFields(view);
    }

    private void updateTextFields(View view) {

        greenText.setText(getString(R.string.counterGreen) + " " + greenCounter);
        goldText.setText(getString(R.string.counterGold) + " " + goldCounter);
        maroonText.setText(getString(R.string.counterMaroon) + " " + maroonCounter);
        purpleText.setText(getString(R.string.counterPurple) + " " + purpleCounter);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("green", greenCounter);
        state.putInt("gold", goldCounter);
        state.putInt("maroon", maroonCounter);
        state.putInt("purple", purpleCounter);

    }

    protected void onRestoreInstanceState(Bundle state) {

        super.onRestoreInstanceState(state);
        greenCounter = state.getInt("green");
        goldCounter = state.getInt("gold");
        maroonCounter = state.getInt("maroon");
        purpleCounter = state.getInt("purple");

        greenText.setText(getString(R.string.counterGreen) + " " + greenCounter);
        goldText.setText(getString(R.string.counterGold) + " " + goldCounter);
        maroonText.setText(getString(R.string.counterMaroon) + " " + maroonCounter);
        purpleText.setText(getString(R.string.counterPurple) + " " + purpleCounter);
    }
}
