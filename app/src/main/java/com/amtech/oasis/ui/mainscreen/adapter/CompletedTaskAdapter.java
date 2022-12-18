package com.amtech.oasis.ui.mainscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.Tasks;

import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>{

    private Context context;
    private List<Tasks> arrayList;

    public CompletedTaskAdapter(Context context) {
        this.context = context;
//        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_completed_task, parent, false);

        CompletedTaskAdapter.ViewHolder holder = new CompletedTaskAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, int position) {
//        Picasso.with(context).load(this.arrayList.get(position).getBannerImage()).into(holder.item_home_horizontal_recycler_view_2_image_view);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_home_horizontal_recycler_view_2_image_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
