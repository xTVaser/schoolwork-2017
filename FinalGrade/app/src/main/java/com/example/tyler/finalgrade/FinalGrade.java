package com.example.tyler.finalgrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FinalGrade extends AppCompatActivity {

    private EditText assignments;
    private EditText exams;
    private TextView gradeOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_grade);

        assignments = (EditText) findViewById(R.id.assignmentField);
        exams = (EditText) findViewById(R.id.examField);
        gradeOutput = (TextView) findViewById(R.id.gradeOutput);

    }

    public void calculateGrade(View view) {

        if (assignments.getText().length() == 0 || exams.getText().length() == 0) {

            Toast.makeText(this, "Please Enter the Above Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int assignmentGrade = (int)Double.parseDouble(assignments.getText().toString());
        int examGrade = (int)Double.parseDouble(exams.getText().toString());

        if (assignmentGrade > 100 || assignmentGrade < 0 || examGrade > 100 || examGrade < 0) {

            Toast.makeText(this, "Invalid Grades, must be between 0-100", Toast.LENGTH_SHORT).show();
            clearGrades(view);
            return;
        }

        gradeOutput.setText("Final Grade is " + (assignmentGrade/2 + examGrade/2) + "%");

    }

    public void clearGrades(View view) {

        assignments.setText("");
        exams.setText("");
        gradeOutput.setText("Please Enter The Above Fields");

        Toast.makeText(this, "Grades Cleared", Toast.LENGTH_SHORT).show();
    }
}
