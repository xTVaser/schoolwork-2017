package com.example.tyler.labquestion1;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    public void addButtons(int buttons) {

        LinearLayout container = (LinearLayout)findViewById(R.id.activity_main);

        for (int i = 0; i < buttons; i++) {

            Button btn = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            btn.setLayoutParams(params);
            btn.setText("BUTTON"+i);
            container.addView(btn);
        }
    }
}
