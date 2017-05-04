package com.example.tyler.imagesfragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDescription extends AppCompatActivity {

    ImageView image;
    TextView title;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);

        Intent intent = getIntent();

        image = (ImageView) findViewById(R.id.descImage);
        title = (TextView) findViewById(R.id.descTitle);
        description = (TextView) findViewById(R.id.descDesc);

        image.setImageResource(intent.getIntExtra("image", 0));
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("desc"));
    }
}
