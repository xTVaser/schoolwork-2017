package com.example.tyler.finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    // ok6qlo1g will be used for testing

    private ArrayList<String> categories = new ArrayList<>();
    private String gameID;

    private DrawerLayout navDrawer;
    private LinearLayout navDrawerContainer;
    private ListView navDrawerItems;

    private ImageButton searchPageBtn;
    private ImageButton favouritePageBtn;

    private Fragment currentFragment;
    private SearchPage searchPageFragment;

    private TextView gameName;
    private Button favouriteBtn;
    private String gamePicURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchPageBtn = (ImageButton) findViewById(R.id.searchPageBtn);
        favouritePageBtn = (ImageButton) findViewById(R.id.favouriteListBtn);

        gameName = (TextView) findViewById(R.id.gameName);
        favouriteBtn = (Button) findViewById(R.id.favouriteBtn);

        navDrawer = (DrawerLayout) findViewById(R.id.main_layout);
        navDrawerContainer = (LinearLayout) findViewById(R.id.left_drawer);
        navDrawerItems = (ListView) findViewById(R.id.navDrawer);

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.add(R.id.fragmentContainer, searchPage);
        currentFragment = searchPage; //TODO: get rid of these, replace with three of a kind
        searchPageFragment = searchPage;
        transaction.commit();
    }

    public void addGameToFavourites(View view) {

        if (gameName.getText().equals(getString(R.string.gameNamePrompt))) {
            Toast.makeText(this, "No currently selected game to add to favourites!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (gameID == null || gameName.getText() == null || gamePicURL == null) {
            Toast.makeText(this, "Vital data missing, error.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store - game name, game id, picture url
        SQLiteDatabase db = openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favourites("+
                   "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                   "id TEXT NOT NULL, "+
                   "name TEXT NOT NULL, "+
                   "picurl TEXT NOT NULL)");

        ContentValues insert = new ContentValues();
        insert.put("id", gameID);
        insert.put("name", gameName.getText().toString());
        insert.put("picurl", gamePicURL);
        db.insert("favourites", null, insert);

        favouriteBtn.setVisibility(View.GONE);
    }

    public void browseGame(View view) {

        String userInput = ((SearchPage)currentFragment).searchField.getText().toString();
        int currentPosition = ((SearchPage)currentFragment).selectedPosition;
        String actualGame = ((SearchPage)currentFragment).gameList.get(currentPosition);
        gameID = ((SearchPage)currentFragment).gameIDs.get(currentPosition);

        if (!actualGame.equals(userInput)) { //could try to do the fuzzy search here TODO: maybe

            Toast.makeText(this, "Error, untracked game", Toast.LENGTH_SHORT).show();
            return;
        }

        updateDrawerAndDisplay(gameID, actualGame);
    }

    protected void updateDrawerAndDisplay(String gameID, String actualGame) {

        String jsonURL = "http://www.speedrun.com/api/v1/games/" + gameID;
        try {

            JSONObject json = new JSONParser(this).execute(jsonURL).get();
            JSONObject gameAssets = json.getJSONObject("data").getJSONObject("assets");

            gamePicURL = gameAssets.getJSONObject("cover-small").getString("uri");
        }
        catch (JSONException | ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        //Update the drawer information TODO: make the drawer stuff look nicer, config the layout
        gameName.setText(actualGame);
        jsonURL = "http://www.speedrun.com/api/v1/games/" + gameID + "/categories";

        try {
            JSONObject json = new JSONParser(this).execute(jsonURL).get();
            JSONArray categoryData = json.getJSONArray("data");

            if (categoryData.length() < 1) {
                Toast.makeText(this, "Game has no runs associated with it.", Toast.LENGTH_SHORT).show();
                return;
            }
            categories = new ArrayList<>();
            for (int i = 0; i < categoryData.length(); i++) {

                JSONObject category = categoryData.getJSONObject(i);
                String test = category.getString("name");
                categories.add(test);
            }
        }
        catch (JSONException | ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        navDrawerItems.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, categories));
        navDrawerItems.setOnItemClickListener(new DrawerItemClickListener());

        SQLiteDatabase db = openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM favourites WHERE id = '" + gameID + "'", null);

        //If we have already favourited it, dont favourite it again
        if (cursor.getCount() > 0)
            favouriteBtn.setVisibility(View.GONE);
        else
            favouriteBtn.setVisibility(View.VISIBLE);

        //Close the soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        launchBrowseTab(0);
    }

    private void launchBrowseTab(int categoryPos) {

        //Hide search button and display progress bar
        searchPageFragment.searchBtn.setVisibility(GONE);
        searchPageFragment.progressBar.setVisibility(View.VISIBLE);

        //Open the run
        FragmentManager fragMang = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("gameID", gameID);
        args.putInt("categoryPos", categoryPos);
        args.putString("title", categories.get(categoryPos));
        FragmentTransaction transaction = fragMang.beginTransaction();
        BrowseRuns browseRuns = new BrowseRuns();
        browseRuns.setArguments(args);
        browseRuns.passFragment(searchPageFragment);
        transaction.replace(R.id.fragmentContainer, browseRuns);
        currentFragment = browseRuns;
        transaction.commit();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            launchBrowseTab(position);
            navDrawer.closeDrawer(navDrawerContainer);
            // TODO: make a loading bar for this as well
        }
    }

    // TODO: fix backstack issues, make it clear out the backstack every now and then
    public void loadSearchPage(View view) {

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.replace(R.id.fragmentContainer, searchPage);
        transaction.addToBackStack(null);
        currentFragment = searchPage;
        searchPageFragment = searchPage;
        transaction.commit();
    }

    public void loadFavouritePage(View view) {

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        FavouriteGames favRuns = new FavouriteGames();
        transaction.replace(R.id.fragmentContainer, favRuns);
        transaction.addToBackStack(null);
        currentFragment = favRuns;
        transaction.commit();
    }
}


