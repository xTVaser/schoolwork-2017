package com.example.tyler.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tyler on 26/03/17.
 */

public class GetRuns implements Runnable {

    private volatile ArrayList<Run> runs = new ArrayList<>();
    private Activity activity;
    private int categoryPos;
    private String gameID;

    private Fragment currentFragment;


    public GetRuns(Activity activity, int categoryPos, String gameID, Fragment currentFragment) {

        this.activity = activity;
        this.categoryPos = categoryPos;
        this.gameID = gameID;

        this.currentFragment = currentFragment;

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

            ((SearchPage)currentFragment).searchBtn.setVisibility(View.VISIBLE);
            ((SearchPage)currentFragment).progressBar.setVisibility(View.GONE);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Run> getValue() {
        return runs;
    }
}
