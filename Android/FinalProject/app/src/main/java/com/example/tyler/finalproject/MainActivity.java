package com.example.tyler.finalproject;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
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
    private String currentGameName;

    private DrawerLayout navDrawer;
    private LinearLayout navDrawerContainer;
    private ListView navDrawerItems;

    private ImageButton searchPageBtn;
    private ImageButton favouritePageBtn;

    private SearchPage searchPageFragment;
    private BrowseRuns browsePageFragment;
    private FavouriteGames favouritePageFragment;

    private TextView gameName;
    private Button favouriteBtn;
    private String gamePicURL;

    private boolean categorySelected = false;
    private int categoryPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableNotifications(getApplication());

        searchPageBtn = (ImageButton) findViewById(R.id.searchPageBtn);
        searchPageBtn.setImageResource(R.drawable.search_active);
        favouritePageBtn = (ImageButton) findViewById(R.id.favouriteListBtn);

        gameName = (TextView) findViewById(R.id.gameName);
        favouriteBtn = (Button) findViewById(R.id.favouriteBtn);

        navDrawer = (DrawerLayout) findViewById(R.id.main_layout);
        navDrawerContainer = (LinearLayout) findViewById(R.id.left_drawer);
        navDrawerItems = (ListView) findViewById(R.id.navDrawer);

        navDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                if (categorySelected) {
                    categorySelected = false;
                    searchPageBtn.setImageResource(R.drawable.search);
                    favouritePageBtn.setImageResource(R.drawable.star);
                    launchBrowseTab(categoryPosition);
                    categoryPosition = 0;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.add(R.id.fragmentContainer, searchPage);
        searchPageFragment = searchPage;
        transaction.commit();
    }

    public void enableNotifications(Application context) {

        Intent intent = new Intent(context, NotificationService.class);
        PendingIntent pending_intent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarm_mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 1000, pending_intent);
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
        db.close();

        favouriteBtn.setVisibility(View.GONE);
    }

    public void browseGame(View view) {

        String userInput = searchPageFragment.searchField.getText().toString();

        if (userInput.equals(""))
            return;

        int currentPosition = searchPageFragment.selectedPosition;
        String actualGame = searchPageFragment.gameList.get(currentPosition);
        gameID = searchPageFragment.gameIDs.get(currentPosition);

        if (!actualGame.equals(userInput)) {

            try {

                String jsonUrl = "http://www.speedrun.com/api/v1/games?name=" + userInput.trim();
                JSONArray fuzzySearch = new JSONParser(this).execute(jsonUrl).get().getJSONArray("data");

                if (fuzzySearch.length() <= 0) { //Still no results
                    Toast.makeText(this, "Error, untracked game", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Grab the first matching result
                JSONObject fuzzyResults = fuzzySearch.getJSONObject(0);

                gameID = fuzzyResults.getString("id");
                actualGame = fuzzyResults.getJSONObject("names").getString("international");
            }
            catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "JSON Error on Fuzzy Search", Toast.LENGTH_SHORT).show();
            }
        }

        searchPageBtn.setImageResource(R.drawable.search);
        favouritePageBtn.setImageResource(R.drawable.star);
        //All it needs is the ID and the name (to avoid another API call)
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

        //Update the drawer information
        gameName.setText(actualGame);
        currentGameName = actualGame;
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
        db.close();

        //Close the soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        // TODO: changing this to be a param would allow for the notification intents to go directly to the right category
        launchBrowseTab(0);
    }

    private void launchBrowseTab(int categoryPos) {

        //Hide search button and display progress bar
        if (searchPageFragment != null) {
            searchPageFragment.searchBtn.setVisibility(View.GONE);
            searchPageFragment.progressBar.setVisibility(View.VISIBLE);
        }

        //Hide favourite page stuff
        if (favouritePageFragment != null) {
            favouritePageFragment.favouriteList.setVisibility(View.GONE);
            favouritePageFragment.progressBar.setVisibility(View.VISIBLE);
        }

        //Hide run page stuff
        if (browsePageFragment != null) {
            browsePageFragment.runList.setVisibility(View.GONE);
            browsePageFragment.progressBar.setVisibility(View.VISIBLE);
        }

        //Open the run
        FragmentManager fragMang = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("gameName", currentGameName);
        args.putString("gameID", gameID);
        args.putInt("categoryPos", categoryPos);
        args.putString("title", categories.get(categoryPos));
        FragmentTransaction transaction = fragMang.beginTransaction();
        BrowseRuns browseRuns = new BrowseRuns();
        browseRuns.setArguments(args);
        Fragment[] fragments = {searchPageFragment, favouritePageFragment};
        browseRuns.passFragments(fragments);
        transaction.replace(R.id.fragmentContainer, browseRuns);
        browsePageFragment = browseRuns;
        transaction.commit();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            categorySelected = true;
            categoryPosition = position;
            navDrawer.closeDrawer(navDrawerContainer);
        }
    }

    public void loadSearchPage(View view) {

        searchPageBtn.setImageResource(R.drawable.search_active);
        favouritePageBtn.setImageResource(R.drawable.star);

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        SearchPage searchPage = new SearchPage();
        transaction.replace(R.id.fragmentContainer, searchPage);
        searchPageFragment = searchPage;
        transaction.commit();
    }

    public void loadFavouritePage(View view) {

        searchPageBtn.setImageResource(R.drawable.search);
        favouritePageBtn.setImageResource(R.drawable.star_active);

        FragmentManager fragMang = getFragmentManager();
        FragmentTransaction transaction = fragMang.beginTransaction();
        FavouriteGames favRuns = new FavouriteGames();
        transaction.replace(R.id.fragmentContainer, favRuns);
        favouritePageFragment = favRuns;
        transaction.commit();
    }
}


