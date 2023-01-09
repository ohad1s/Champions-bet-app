package com.example.champions.DAL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    public static final String ARG_MESSAGE = "message";

    public static MyDialogFragment newInstance(String message) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(ARG_MESSAGE);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

