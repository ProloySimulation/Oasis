package com.amtech.oasis.ui.detailscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.CheckList;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;

import java.util.ArrayList;

public class PendingCheckListAdapter extends RecyclerView.Adapter<PendingCheckListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<CheckList> arrayList;

    public PendingCheckListAdapter(Context context, ArrayList<CheckList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PendingCheckListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_check_list, parent, false);

        PendingCheckListAdapter.ViewHolder holder = new PendingCheckListAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingCheckListAdapter.ViewHolder holder, int position) {

        for (int i =0;i<this.arrayList.size();i++)
        {
            holder.tvCheckName.setText(this.arrayList.get(i).getCheckListName());
        }

//        Picasso.with(context).load(this.arrayList.get(position).getBannerImage()).into(holder.item_home_horizontal_recycler_view_2_image_view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvCheckName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCheckName = itemView.findViewById(R.id.tvPendingCheckList);
        }
    }

    public interface ClickListener
    {
        void itemClickCompleted(int position);
        void itemClickPending(int position);
        void itemClickAllAssign(int position);
    }
}
