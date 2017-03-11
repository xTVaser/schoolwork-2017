package com.example.tyler.imagesfragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Images extends AppCompatActivity {

    private ImageButton hsButton;
    private ImageButton clButton;
    private ImageButton gitButton;
    private ImageButton scButton;
    private ImageButton elButton;
    private ImageButton rButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        hsButton = (ImageButton) findViewById(R.id.haskell);
        clButton = (ImageButton) findViewById(R.id.lisp);
        gitButton = (ImageButton) findViewById(R.id.git);
        scButton = (ImageButton) findViewById(R.id.scala);
        elButton = (ImageButton) findViewById(R.id.erlang);
        rButton = (ImageButton) findViewById(R.id.r);

        final AppCompatActivity app = this;

        hsButton.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(app, ImageDescription.class);
                intent.putExtra("image", R.drawable.haskell);
                intent.putExtra("title", "Haskell from First Principles");
                intent.putExtra("desc", "The first haskell book to a fundamental and ground up " +
                        "approach to teaching the fairly difficult to understand at first language.");

                startActivity(intent);
            }
        });
    }
}
