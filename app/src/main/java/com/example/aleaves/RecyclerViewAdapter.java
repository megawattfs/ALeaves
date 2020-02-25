package com.example.aleaves;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<String> itemList;
    protected Context context;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public RecyclerViewAdapter(Context context, List<String> itemList, RecyclerView recyclerView) {
        this.itemList = itemList;
        this.context = context;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? 1 : 0;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        if(viewType == 1){
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view_leaves, parent, false);
            viewHolder = new RecyclerViewHolders(layoutView);
        }else{
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view_leaves, parent, false);
            viewHolder = new ProgressViewHolder(layoutView);
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        if(holder instanceof RecyclerViewHolders){
            ((RecyclerViewHolders)holder).textTitle.setText(itemList.get(position));
        }else{
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }
    public void setLoad(){
        loading = false;
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setLoaded() {
        loading = false;
    }
}