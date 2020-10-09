package com.example.aleaves;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ViewLeaves extends AppCompatActivity {
    private static final String TAG = "ViewLeaves";
    private RecyclerView recyclerView;
    private LeafCaptureAdapter leafCaptureAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<LeafCapture> leafCaptureList;
    private RemoteFindIterable<LeafCapture> documentList;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leaves);
        handler = new Handler();
        gridLayoutManager = new GridLayoutManager(ViewLeaves.this, 3);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        leafCaptureList = new ArrayList<>();

        new DBLoader().execute();
    }

    private class DBLoader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int numLeavesFound = 0;
            while(numLeavesFound == 0) {
                documentList = MainActivity.all_leaves.find();//Returns all leaves in the database
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);
            Log.d("aleaves","document list generated:");
            documentList.into(leafCaptureList);
            leafCaptureAdapter = new LeafCaptureAdapter(ViewLeaves.this, leafCaptureList);
            recyclerView.setAdapter(leafCaptureAdapter);
            Log.d("viewleaves", Integer.valueOf(leafCaptureList.size()).toString());
        }
    }
}