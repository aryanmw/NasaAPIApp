package com.wadhavekar.nasainfo.UIclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wadhavekar.nasainfo.R;

import java.util.List;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.ViewHolder> {

    Context mContext;
    List<FavouritesObject> favList;

    public FavListAdapter(Context mContext, List<FavouritesObject> favList) {
        this.mContext = mContext;
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavListAdapter.ViewHolder holder, int position) {
        holder.title.setText(favList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return favList.size();
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
                        String fr = favList.get(pos).getTitle();
                        Intent intent = new Intent(mContext, APOD.class);
                        intent.putExtra("title",fr);
                        intent.putExtra("date", favList.get(pos).getDate());
                        mContext.startActivity(intent);
                        //Toast.makeText(context, "You clicked "+fr, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}