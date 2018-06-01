package com.br.apigithub.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rlima on 31/05/18.
 */

public class IssueFragment extends Fragment {

    public static IssueFragment newInstance() {
        IssueFragment issueFragment = new IssueFragment();
        return issueFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(null, container, false);
        return v;
    }
}
