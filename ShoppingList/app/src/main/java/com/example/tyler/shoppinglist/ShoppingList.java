package com.example.tyler.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {

    private EditText input;
    private Button addBtn;
    private ListView shoppingList;

    private ArrayList<String> shoppingItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        input = (EditText) findViewById(R.id.input);
        addBtn = (Button) findViewById(R.id.addItem);
        shoppingList = (ListView) findViewById(R.id.shoppingList);

        shoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                shoppingItems.remove(position);
                updateList();
                return true;
            }
        });
    }

    public void addShoppingItem(View view) {

        shoppingItems.add(input.getText().toString());
        updateList();
    }

    private void updateList() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingItems);
        shoppingList.setAdapter(adapter);
    }
}
