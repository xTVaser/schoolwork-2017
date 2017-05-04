package com.example.tyler.imagesfragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

class Tuple<X, Y, Z> {

    public X x;
    public Y y;
    public Z z;

    public Tuple(X x, Y y, Z z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }
}

public class Images extends AppCompatActivity {

    private ImageButton hsButton;
    private ImageButton clButton;
    private ImageButton gitButton;
    private ImageButton scButton;
    private ImageButton elButton;
    private ImageButton rButton;

    private boolean landscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration conf = getResources().getConfiguration();

        if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE)
            landscape = true;
        else
            landscape = false;

        setContentView(R.layout.activity_images);

        hsButton = (ImageButton) findViewById(R.id.haskell);
        clButton = (ImageButton) findViewById(R.id.lisp);
        gitButton = (ImageButton) findViewById(R.id.git);
        scButton = (ImageButton) findViewById(R.id.scala);
        elButton = (ImageButton) findViewById(R.id.erlang);
        rButton = (ImageButton) findViewById(R.id.r);

        final AppCompatActivity app = this;

        hsButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.haskell,
                                                            "Haskell from First Principles",
                                                            "The first haskell book to a fundamental and ground up " +
                                                                "approach to teaching the fairly difficult to understand at first language.");
            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });

        clButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.lisp,
                                                            "Lisp Outside the Box",
                                                            "Common Lisp is a dialect of the Lisp programming language, published in ANSI standard document ANSI INCITS 226-1994.");

            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });

        gitButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.git,
                    "Git Pocket Guide",
                    "Git is a version control system for tracking changes in computer files and coordinating work on those files among multiple people.");


            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });

        scButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.scala,
                    "Scala Cookbook",
                    "Scala is a general-purpose programming language providing support for functional programming and a strong static type system.");


            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });

        elButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.erlang,
                    "Erlang Programming",
                    "Erlang is a general-purpose, concurrent, functional programming language, as well as a garbage-collected runtime system.");


            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });

        rButton.setOnClickListener(new ImageButton.OnClickListener() {

            Tuple<Integer, String, String> info = new Tuple<>(R.drawable.r,
                    "R for Data Science",
                    "R is an open source programming language and software environment for statistical computing and graphics that is supported by the R Foundation for Statistical Computing.");


            @Override
            public void onClick(View v) {

                if (landscape) {
                    ImageFragment frag = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDisplay);
                    frag.updateViews(info.x, info.y, info.z);
                }
                else {
                    startActivity(createIntent(info.x, info.y, info.z));
                }
            }
        });
    }

    private Intent createIntent(int resource, String title, String desc) {

        Intent intent = new Intent(this, ImageDescription.class);
        intent.putExtra("image", resource);
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);

        return intent;
    }

    @Override
    public void onConfigurationChanged(Configuration conf) {
        super.onConfigurationChanged(conf);

        if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE)
            landscape = true;
        else if (conf.orientation == Configuration.ORIENTATION_PORTRAIT)
            landscape = false;
    }
}
