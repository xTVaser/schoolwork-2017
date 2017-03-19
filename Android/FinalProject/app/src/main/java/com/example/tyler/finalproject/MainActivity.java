package com.example.tyler.finalproject;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private String[] test = {"yes", "no", "maybe"};
    private DrawerLayout navDrawer;
    private ListView navDrawerItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navDrawer = (DrawerLayout) findViewById(R.id.main_layout);
        navDrawerItems = (ListView) findViewById(R.id.navDrawer);

        navDrawerItems.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, test));
        navDrawerItems.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        // the navigation android example has a good example of adding a fragment dynamically here
        makeText(this, "Test", Toast.LENGTH_LONG).show();
    }
}


