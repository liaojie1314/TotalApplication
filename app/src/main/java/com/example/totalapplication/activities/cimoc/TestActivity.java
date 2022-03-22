package com.example.totalapplication.activities.cimoc;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.example.totalapplication.R;
import com.example.totalapplication.base.BaseActivity;
import com.just.agentweb.AgentWeb;

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        AgentWeb.with(this)
                .setAgentWebParent(new LinearLayout(this), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://manhua.fzdm.com/2//937/index_2.html");
    }
}
