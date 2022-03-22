package com.example.totalapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.totalapplication.Utils.StatusBarUtil;
import com.example.totalapplication.activities.MainActivity;

public class BaseFragment extends Fragment {
    protected boolean isInitialized = false;
    protected boolean isDark = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected ActionBar setToolbar(int toolbarId) {
//        setHasOptionsMenu(true);
        MainActivity mainActivity = (MainActivity) getActivity();
        Toolbar toolbar = mainActivity.findViewById(toolbarId);
        mainActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            return actionBar;
        }
        return null;
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToastWithLongShow(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void setDarkStatus(boolean isDark) {
        this.isDark = isDark;
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), isDark);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDarkStatus(isDark);
    }
}
