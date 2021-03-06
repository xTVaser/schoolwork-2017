package com.example.tyler.finalproject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GetRuns implements Runnable {

    private volatile ArrayList<Run> runs = new ArrayList<>();
    private Activity activity;
    private int categoryPos;
    private String gameID;

    private SearchPage searchPageFragment;
    private FavouriteGames favouritePageFragment;
    private BrowseRuns browsePageFragment;


    public GetRuns(Activity activity, int categoryPos, String gameID, SearchPage searchPageFragment,
                   BrowseRuns browsePageFragment, FavouriteGames favouritePageFragment) {

        this.activity = activity;
        this.categoryPos = categoryPos;
        this.gameID = gameID;

        this.searchPageFragment = searchPageFragment;
        this.browsePageFragment = browsePageFragment;
        this.favouritePageFragment = favouritePageFragment;
    }

    @Override
    public void run() {

        String jsonURL = "http://www.speedrun.com/api/v1/games/" + gameID + "/categories";

        try {
            JSONObject json = new JSONParser(activity).execute(jsonURL).get();
            JSONArray categoryData = json.getJSONArray("data");

            JSONArray links = categoryData.getJSONObject(categoryPos).getJSONArray("links");

            for (int i = 0; i < links.length(); i++) {

                JSONObject currentLink = links.getJSONObject(i);
                if (currentLink.getString("rel").equals("leaderboard")) {

                    jsonURL = currentLink.getString("uri");
                    break;
                }
            }

            json = new JSONParser(activity).execute(jsonURL).get();

            JSONArray runData = json.getJSONObject("data").getJSONArray("runs");

            final int incrementAmount = runData.length() / 100;

            for (int i = 0; i < runData.length(); i++) {

                JSONObject currentRun = runData.getJSONObject(i);
                JSONObject currentRunInfo = currentRun.getJSONObject("run");

                JSONObject player = currentRunInfo.getJSONArray("players").getJSONObject(0);
                String runnerName;

                if (player.getString("rel").equals("guest"))
                    runnerName = player.getString("name");
                else {

                    JSONObject tempJson = new JSONParser(activity).execute(player.getString("uri")).get();
                    runnerName = tempJson.getJSONObject("data").getJSONObject("names").getString("international");
                }

                Long time = Long.parseLong(currentRunInfo.getJSONObject("times").getString("primary_t"));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date submissionDate = dateFormat.parse(currentRunInfo.getString("date"));
                String runLink = currentRunInfo.getString("weblink");

                //Create Run
                Run newRun = new Run(currentRun.getInt("place"), runnerName, time, submissionDate, runLink);
                runs.add(newRun);
            }

            if (searchPageFragment != null) {
                searchPageFragment.searchBtn.setVisibility(View.VISIBLE);
                searchPageFragment.progressBar.setVisibility(View.GONE);
            }

            if (browsePageFragment != null) {
                browsePageFragment.runList.setVisibility(View.VISIBLE);
                browsePageFragment.progressBar.setVisibility(View.GONE);
            }

            if (favouritePageFragment != null) {
                favouritePageFragment.favouriteList.setVisibility(View.VISIBLE);
                favouritePageFragment.progressBar.setVisibility(View.GONE);
            }


        }
        catch (Exception e) {
            Log.e("JSONError", e.getMessage());
        }
    }

    public ArrayList<Run> getValue() {
        return runs;
    }
}
