package com.example.tyler.finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Scanner;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    // ok6qlo1g will be used for testing

    private String[] test = {"Category 1", "Category 2", "Category 3", "Category 4", "Category 5"};
    private DrawerLayout navDrawer;
    private ListView navDrawerItems;

    private ImageButton searchPageBtn;
    private ImageButton favouritePageBtn;

    private Fragment currentFragment;

    private TextView gameName;
    private Button favouriteBtn;

    private ArrayList<String> gameList;
    private ArrayList<String> gameIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Scanner in = new Scanner(getResources().openRawResource(R.raw.gamelist));

        while(in.hasNext()) {

            String[] line = in.nextLine().split("\t");
            gameList.add(line[0]);
            gameIDs.add(line[1]);
        }

        in.close();

        searchPageBtn = (ImageButton) findViewById(R.id.searchPageBtn);
        favouritePageBtn = (ImageButton) findViewById(R.id.favouriteListBtn);

        gameName = (TextView) findViewById(R.id.gameName);
        favouriteBtn = (Button) findViewById(R.id.favouriteBtn);

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.add(R.id.fragmentContainer, searchPage);
        transaction.addToBackStack(null);
        transaction.commit();


        navDrawer = (DrawerLayout) findViewById(R.id.main_layout);
        navDrawerItems = (ListView) findViewById(R.id.navDrawer);

        navDrawerItems.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, test));
        navDrawerItems.setOnItemClickListener(new DrawerItemClickListener());

    }

    public void addGameToFavourites(View view) {

        if (gameName.getText().equals(getString(R.string.gameNamePrompt)))
            Toast.makeText(this, "No currently selected game to add to favourites!", Toast.LENGTH_SHORT).show();

        // TODO: database interaction here, add the game ID to favourites

    }

    public void browseGame(View view) {

        Toast.makeText(this, "does this work?", Toast.LENGTH_SHORT).show();
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




    public void loadSearchPage(View view) {

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.replace(R.id.fragmentContainer, searchPage);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFavouritePage(View view) {

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        FavouriteRuns favRuns = new FavouriteRuns();
        transaction.replace(R.id.fragmentContainer, favRuns);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}


