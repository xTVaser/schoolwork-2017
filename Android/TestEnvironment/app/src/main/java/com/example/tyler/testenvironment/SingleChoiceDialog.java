package com.example.tyler.testenvironment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SingleChoiceDialog extends DialogFragment {

    public SingleChoiceDialog() {
        //required empty
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] listItems = {"Option1", "Option2", "Option3", "Option4"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("CHANGE TITLE");
        dialog.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), which + "CHANGE TOAST", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNeutralButton("Neutral Button", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO:STUFF
            }
        });

        return dialog.create();
    }
}
