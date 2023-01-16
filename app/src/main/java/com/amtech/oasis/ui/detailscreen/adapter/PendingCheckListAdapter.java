package com.amtech.oasis.ui.detailscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.CheckList;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.ui.mainscreen.adapter.CompletedTaskAdapter;

import java.util.ArrayList;

public class PendingCheckListAdapter extends RecyclerView.Adapter<PendingCheckListAdapter.ViewHolder>{

    private Context context;
    private boolean viewable;
    private ArrayList<CheckList> arrayList;
    private ClickListener clickListener;

    public PendingCheckListAdapter(Context context, ArrayList<CheckList> arrayList,boolean viewable,ClickListener clickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.viewable = viewable;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PendingCheckListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_check_list, parent, false);

        PendingCheckListAdapter.ViewHolder holder = new PendingCheckListAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingCheckListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvCheckName.setText(this.arrayList.get(position).getCheckListName());

        if(this.arrayList.get(position).getImageCheck().equals("1"))
        {
            holder.layoutSelection.setVisibility(View.GONE);
            holder.tvUploadImage.setVisibility(View.VISIBLE);
        }

        if(this.arrayList.get(position).getCheckStatus().equals("1"))
        {
            holder.parentLayout.setVisibility(View.GONE);
        }

        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(position);
                holder.tvCheckName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });*/

        if(!viewable)
        {
            holder.tvUploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.itemClick(position);
                    holder.tvCheckName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            });
        }
        else
        {
            holder.rbYes.setEnabled(false);
            holder.rbNo.setEnabled(false);
        }

        holder.rbCheckList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < holder.rbCheckList.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) holder.rbCheckList.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        String text = (String) btn.getText();
                        clickListener.checkList(position,text);
                        return;
                    }
                }
            }
        });

//        Picasso.with(context).load(this.arrayList.get(position).getBannerImage()).into(holder.item_home_horizontal_recycler_view_2_image_view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvCheckName,tvUploadImage;
        LinearLayoutCompat parentLayout,layoutSelection;
        RadioGroup rbCheckList;
        RadioButton rbYes,rbNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCheckName = itemView.findViewById(R.id.tvPendingCheckList);
            tvUploadImage = itemView.findViewById(R.id.tvUploadImageCheck);
            parentLayout = itemView.findViewById(R.id.checkListParentLayout);
            layoutSelection = itemView.findViewById(R.id.layoutSelection);
            rbCheckList = itemView.findViewById(R.id.radioGroupCheckList);

            rbYes = itemView.findViewById(R.id.radioButtonPositive);
            rbNo = itemView.findViewById(R.id.radioButtonNegative);
        }
    }

    public interface ClickListener
    {
        void itemClick(int position);
        void checkList(int position,String checkAns);
    }
}
