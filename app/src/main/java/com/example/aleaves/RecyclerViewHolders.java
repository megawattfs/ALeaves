package com.example.aleaves;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView displayedImage;
    public TextView textTitle;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textTitle = (TextView)itemView.findViewById(R.id.title_header);
    }
    @Override
    public void onClick(View view) {
    }
}