package com.example.aleaves;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewLeaves extends AppCompatActivity {
    private static final String TAG = "ViewLeaves";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private  List<String> adapterData;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        linearLayoutManager = new LinearLayoutManager(ViewLeaves.this);
        // return the data object
        adapterData = getFirstData();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(ViewLeaves.this, adapterData, recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                adapterData.add(null);
                recyclerViewAdapter.notifyItemInserted(adapterData.size() - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterData.remove(adapterData.size() - 1);
                        recyclerViewAdapter.notifyItemRemoved(adapterData.size());
                        for (int i = 0; i < 15; i++) {
                            adapterData.add("Item" + (adapterData.size() + 1));
                            recyclerViewAdapter.notifyItemInserted(adapterData.size());
                        }
                        recyclerViewAdapter.setLoaded();
                    }
                }, 2000);
                System.out.println("load");
            }
        });
    }
    private List<String> getFirstData(){
        List<String> listObject = new ArrayList<String>();
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        listObject.add("one");
        return listObject;
    }
}