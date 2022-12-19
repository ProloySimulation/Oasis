package com.amtech.oasis.ui.mainscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.Tasks;

import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>{

    private Context context;
    private List<Tasks> arrayList;
    private ClickListener clickListener;
    private String taskType;

    public CompletedTaskAdapter(Context context,String taskType,ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.taskType = taskType;
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

        if(taskType.equals("completed"))
        {
            holder.tableCompleted.setVisibility(View.VISIBLE);
        }

        if(taskType.equals("pending"))
        {
            holder.tablePending.setVisibility(View.VISIBLE);
        }

        if(taskType.equals("assigned"))
        {
            holder.tableAllAssigned.setVisibility(View.VISIBLE);
        }

        holder.imvCompletedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickCompleted(position);
            }
        });

        holder.imvPendingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickPending(position);
            }
        });

        holder.imvAssignedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickAllAssign(position);
            }
        });
//        Picasso.with(context).load(this.arrayList.get(position).getBannerImage()).into(holder.item_home_horizontal_recycler_view_2_image_view);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvCompletedView,imvPendingView,imvAssignedView;
        TableLayout tableCompleted,tablePending,tableAllAssigned;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvCompletedView = itemView.findViewById(R.id.imvViewCompletedTask);
            imvPendingView = itemView.findViewById(R.id.imvPendingView);
            imvAssignedView = itemView.findViewById(R.id.imvViewAllAssigned);

            tableCompleted = itemView.findViewById(R.id.tableCompleted);
            tablePending = itemView.findViewById(R.id.tablePending);
            tableAllAssigned = itemView.findViewById(R.id.tableAllAssigned);
        }
    }

    public interface ClickListener
    {
        void itemClickCompleted(int position);
        void itemClickPending(int position);
        void itemClickAllAssign(int position);
    }
}
