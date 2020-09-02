package com.wadhavekar.nasainfo.UIclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint.Items;
import com.wadhavekar.nasainfo.R;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Items> itemList;

    public SearchRecyclerViewAdapter(Context context, List<Items> data) {
        this.context = context;
        this.itemList = data;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(itemList.get(position).getData().get(0).getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_searchItem);

            //onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        String fr = itemList.get(pos).getData().get(0).getNasaId();
                        Intent intent = new Intent(context, AssetImage.class);
                        intent.putExtra("nasa_id",fr);
                        intent.putExtra("title", itemList.get(pos).getData().get(0).getTitle());
                        context.startActivity(intent);
                        //Toast.makeText(context, "You clicked "+fr, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
