package com.example.aleaves;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;

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
        documentList = MainActivity.all_leaves.find();//Returns all leaves in the database
        documentList.forEach( (element) -> {
            leafCaptureList.add( element );
            Log.d("arraylist","element added");});
        while(leafCaptureList.size() == 0) {
        }
        Log.d("viewleaves", Integer.valueOf(leafCaptureList.size()).toString());
        leafCaptureAdapter = new LeafCaptureAdapter(ViewLeaves.this, leafCaptureList);
        recyclerView.setAdapter(leafCaptureAdapter);

        /*TODO do we need this at all?
        leafCaptureAdapter.setOnLoadMoreListener(new LeafCaptureAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                leafCaptureList.add(null);
                leafCaptureAdapter.notifyItemInserted(leafCaptureList.size() - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        leafCaptureList.remove(leafCaptureList.size() - 1);
                        leafCaptureAdapter.notifyItemRemoved(leafCaptureList.size());
                        for (int i = 0; i < 15; i++) {
                            leafCaptureList.add("Item" + (leafCaptureList.size() + 1));
                            leafCaptureAdapter.notifyItemInserted(leafCaptureList.size());
                        }
                        leafCaptureAdapter.setLoaded();
                    }
                }, 2000);
                System.out.println("load");
            }
        });*/
    }
}