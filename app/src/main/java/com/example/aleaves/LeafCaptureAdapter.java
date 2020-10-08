package com.example.aleaves;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class LeafCaptureAdapter extends RecyclerView.Adapter<LeafCaptureAdapter.LeafCaptureViewHolder> {
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<LeafCapture> leafCaptureList;

    //getting the context and product list with constructor
    public LeafCaptureAdapter(Context mCtx, List<LeafCapture> leafCaptureList) {
        this.mCtx = mCtx;
        this.leafCaptureList = leafCaptureList;
    }

    @Override
    public LeafCaptureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.content_view_leaves, null);
        return new LeafCaptureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeafCaptureViewHolder holder, int position) {
        //getting the product of the specified position
        LeafCapture leafCapture = leafCaptureList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(leafCapture.getDate().toString());
        holder.imageView.setImageDrawable(leafCapture.getDrawable());

    }


    @Override
    public int getItemCount() {
        return leafCaptureList.size();
    }


    class LeafCaptureViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public LeafCaptureViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.card_text);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}