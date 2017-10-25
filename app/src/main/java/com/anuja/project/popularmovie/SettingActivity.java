package com.anuja.project.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by ANUJA on 24-01-2017.
 */

public class SettingActivity  extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }
            return super.onOptionsItemSelected(item);
        }
    }


