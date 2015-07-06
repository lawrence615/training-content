package com.mobidev.tabsexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by lawrence on 7/6/15.
 */
public class Login extends Fragment {

    private EditText edUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        edUsername = (EditText) rootView.findViewById(R.id.username);
//        edUsername.post(new Runnable() {
//            public void run() {
//                edUsername.requestFocusFromTouch();
//                InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                lManager.showSoftInput(edUsername, 0);
//            }
//        });

        return rootView;
    }
}
