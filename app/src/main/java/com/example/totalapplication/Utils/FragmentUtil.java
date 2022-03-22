package com.example.totalapplication.Utils;


import androidx.fragment.app.Fragment;

import com.example.totalapplication.fragments.BookshelfFragment;
import com.example.totalapplication.fragments.CimocHomeFragment;
import com.example.totalapplication.fragments.HomeFragment;
import com.example.totalapplication.fragments.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentUtil {
    private static List<Fragment> fragmentList;

    public static void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new CimocHomeFragment());
        fragmentList.add(new BookshelfFragment());
        fragmentList.add(new UserFragment());
    }

    public static Fragment getFragment(int fragmentIndex) {
        if (fragmentList == null) {
            initFragments();
        }
        return fragmentList.get(fragmentIndex);
    }
}
