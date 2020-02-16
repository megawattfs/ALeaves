package com.example.aleaves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddLeaves(View view) {
        Intent addLeavesIntent = new Intent(this, AddLeaves.class);
        startActivity(addLeavesIntent);
    }
}
