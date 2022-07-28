package com.example.allbarber.modelo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AllFragment extends DialogFragment {

    public static final String ARGUMENTO_TITLE = "TITLE";
    public static final String ARGUMENTO_FULL_SNIPPET = "FULL_SNIPPET";

    private String title;
    private String fullSnippet;

    public static AllFragment newInstance(String title, String fullSnippet){
        AllFragment fragment = new AllFragment();
        Bundle b = new Bundle();
        b.putString(ARGUMENTO_TITLE,title);
        b.putString(ARGUMENTO_FULL_SNIPPET,fullSnippet);
        fragment.setArguments(b);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        title = args.getString(ARGUMENTO_TITLE);
        fullSnippet = args.getString(ARGUMENTO_FULL_SNIPPET);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(fullSnippet)
                .create();
        return dialog;
    }

}
