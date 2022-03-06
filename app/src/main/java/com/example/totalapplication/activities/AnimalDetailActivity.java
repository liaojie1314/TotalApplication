package com.example.totalapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.totalapplication.R;
import com.google.android.material.appbar.AppBarLayout;

public class AnimalDetailActivity extends AppCompatActivity {

    private String TAG = "AnimalDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        AppBarLayout appBar = findViewById(R.id.appbar);
        ImageView animalDetail = findViewById(R.id.img_animal_detail);
        Toolbar toolbarDetail = findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView nameDetailText = findViewById(R.id.tv_name_detail);
        Intent intent = getIntent();
        int pic = intent.getIntExtra("pic", 1);
        String name = intent.getStringExtra("name");
        Glide.with(getBaseContext()).load(pic).into(animalDetail);
        String newName = "";
        for (int i = 0; i < 10; i++) {
            newName += name;
        }
        nameDetailText.setText(newName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}