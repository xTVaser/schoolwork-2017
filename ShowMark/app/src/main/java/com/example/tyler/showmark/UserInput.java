package com.example.tyler.showmark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInput extends AppCompatActivity {

    private Button submit;
    private EditText input_id;
    private EditText input_name;

    private HashMap students = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        submit = (Button) findViewById(R.id.submitButton);
        input_id = (EditText) findViewById(R.id.input_id);
        input_name = (EditText) findViewById(R.id.input_name);

        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.exammarks));

        while(scanner.hasNext()) {

            String[] line = scanner.nextLine().split("\\s");
            students.put(line[0] + "_" + line[1], line[2]);
        }

        scanner.close();
    }

    public void onSubmit(View view) {

        String id = input_id.getText().toString();
        String name = input_name.getText().toString();

        if(id.equals("") || name.equals(""))
            Toast.makeText(this, "Please enter the Fields Above", Toast.LENGTH_SHORT).show();

        else if(students.containsKey(name + "_" + id) == false) {

            //Would be better to not say the problem with each individual field since both together are the key
            //but anything to fulfill the requirements...
            ArrayList<String> keys = new ArrayList<String>(students.keySet());

            for (int i = 0; i < keys.size(); i++) {

                if (keys.get(i).contains(name)) {
                    Toast.makeText(this, "Wrong Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(keys.get(i).contains(id)) {
                    Toast.makeText(this, "Student Name does not Exist", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(this, "Student Not Found", Toast.LENGTH_SHORT).show();

        }


        else {
            Intent displayMark = new Intent(this, DisplayMark.class);
            displayMark.putExtra("name", name);
            displayMark.putExtra("mark", students.get(name + "_" + id).toString());
            startActivity(displayMark);
        }
    }
}
