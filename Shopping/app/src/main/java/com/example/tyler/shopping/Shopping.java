package com.example.tyler.shopping;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

class Pair<X,Y> {

    final X x;
    final Y y;
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

}
public class Shopping extends AppCompatActivity {

    private ArrayList<Pair<EditText, Double>> bookQuantities = new ArrayList<>();
    private Button checkout;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final AppCompatActivity activity = this;

        LinearLayout root = (LinearLayout) findViewById(R.id.activity_shopping);
            root.setOrientation(LinearLayout.VERTICAL);
            root.setGravity(Gravity.CENTER_HORIZONTAL);

        ScrollView scrollPane = new ScrollView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixels(0), 10f);
            scrollPane.setLayoutParams(params);

        LinearLayout container = new LinearLayout(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            container.setLayoutParams(params);
            container.setOrientation(LinearLayout.VERTICAL);

        checkout = new Button(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixels(0), 1f);
            params.topMargin = dpToPixels(15);
            checkout.setLayoutParams(params);
            checkout.setText("Checkout");

        checkout.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                double totalCost = 0;

                for (Pair<EditText, Double> book : bookQuantities) {

                    String quantity_String = book.x.getText().toString();

                    if (quantity_String.equals(""))
                        continue;

                    double quantity = Double.parseDouble(quantity_String);
                    totalCost += quantity * book.y; //Quantity * Cost

                }

                total.setText("Total: " + totalCost);
            }
        });

        total = new TextView(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixels(0), 1f);
            total.setText("Total: $0.00");
            total.setGravity(Gravity.CENTER);

        root.addView(scrollPane);
            scrollPane.addView(container);
                addBook(container, R.drawable.haskell, 24.50, "Haskell Programming from First Principles");
                addBook(container, R.drawable.erlang, 50.25, "Erlang Programming");
                addBook(container, R.drawable.git, 15.25, "Git Pocket Guide");
                addBook(container, R.drawable.lisp, 40.75, "Lisp Outside the Box");
                addBook(container, R.drawable.scala, 37.75, "Scala Cookbook");
        root.addView(checkout);
        root.addView(total);

    }

    private void addBook(LinearLayout container, int resource, double cost, String name) {

        GridLayout bookContainer = new GridLayout(this); //By default already wraps content
        bookContainer.setUseDefaultMargins(true);
        bookContainer.setColumnCount(2);
        bookContainer.setRowCount(2);

        ImageView book = new ImageView(this);
        book.setImageResource(resource);
        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
        gridParams.bottomMargin = dpToPixels(25);
        gridParams.height = dpToPixels(150);
        gridParams.width = dpToPixels(125);
        gridParams.rowSpec = GridLayout.spec(0, 2);
        gridParams.columnSpec = GridLayout.spec(0);
        book.setLayoutParams(gridParams);

        TextView price = new TextView(this);
        price.setText("Price: " + cost);
        price.setTextSize(16f);
        price.setWidth(dpToPixels(200));
        price.setGravity(Gravity.CENTER);
        gridParams = new GridLayout.LayoutParams();
        gridParams.topMargin = dpToPixels(50);
        gridParams.columnSpec = GridLayout.spec(1);
        gridParams.rowSpec = GridLayout.spec(0);
        price.setLayoutParams(gridParams);

        EditText quantity = new EditText(this);
        gridParams = new GridLayout.LayoutParams();
        gridParams.columnSpec = GridLayout.spec(1);
        gridParams.rowSpec = GridLayout.spec(1);
        quantity.setWidth(dpToPixels(200));
        quantity.setGravity(Gravity.CENTER);
        quantity.setHint("0");
        quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        quantity.setLayoutParams(gridParams);

        bookContainer.addView(book);
        bookContainer.addView(price);
        bookContainer.addView(quantity);

        container.addView(bookContainer);

        bookQuantities.add(new Pair<EditText, Double>(quantity, cost));
    }

    private int dpToPixels(int dps) {

        float scale = getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
