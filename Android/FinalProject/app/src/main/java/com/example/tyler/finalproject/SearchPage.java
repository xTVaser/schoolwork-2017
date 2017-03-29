package com.example.tyler.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchPage extends Fragment {

    protected AutoCompleteTextView searchField;
    protected Button searchBtn;
    protected ProgressBar progressBar;

    protected ArrayList<String> gameList = new ArrayList<>();
    protected ArrayList<String> gameIDs = new ArrayList<>();
    protected int selectedPosition;

    public SearchPage() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Scanner in = new Scanner(getResources().openRawResource(R.raw.gamelist));

        while(in.hasNext()) {

            String[] line = in.nextLine().split("\t");
            gameIDs.add(line[0]);
            gameList.add(line[1]);
        }

        in.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_page, container, false);
        searchBtn = (Button) rootView.findViewById(R.id.browseGame);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        //Setting the search text field
        searchField = (AutoCompleteTextView) rootView.findViewById(R.id.userInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, gameList);
        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedPosition = gameList.indexOf(searchField.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
