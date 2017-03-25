package com.example.tyler.labquestion1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by tyler on 20/03/17.
 */

public class SingleChoiceDialog extends DialogFragment {

    public SingleChoiceDialog() {
        //required empty
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] listItems = {"1", "2", "3", "4"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Choose a number");
        dialog.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getActivity()).addButtons(which+1);
            }
        });

        return dialog.create();
    }
}
