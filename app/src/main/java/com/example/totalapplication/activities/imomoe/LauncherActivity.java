package com.example.totalapplication.activities.imomoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.totalapplication.R;
import com.example.totalapplication.api.imomoeAPI.ImomoeSearch;
import com.example.totalapplication.base.MyImomoeApplication;
import com.example.totalapplication.managers.ImomoeManager;

import java.util.ArrayList;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ImomoeManager.getInstance().getBangumiSearch("天", 1).subscribe(new Observer<ArrayList<ImomoeSearch>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("momoa", "start");
            }

            @Override
            public void onNext(ArrayList<ImomoeSearch> imomoeSearches) {
                MyImomoeApplication.arrayList.addAll(imomoeSearches);
                Intent intent = new Intent(LauncherActivity.this, SearchTitleBarActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("momoa", e.toString());
                Toast.makeText(LauncherActivity.this, "发生错误, 请检查您的网络", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LauncherActivity.this, SearchTitleBarActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onComplete() {
                Log.i("momoa", "done");
            }
        });
    }
}
