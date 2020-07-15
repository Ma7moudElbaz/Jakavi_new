package com.cat.newname.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cat.newname.R;
import com.cat.newname.model_items.FLM_item;
import com.cat.newname.model_items.Patient_item;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FLM_adapter extends RecyclerView.Adapter<FLM_adapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<FLM_item> items;

    private Context mContext;

    public FLM_adapter(Context context, ArrayList<FLM_item> items) {

        this.mContext = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FLM_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flm, parent, false);
        FLM_adapter.ViewHolder holder = new FLM_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FLM_adapter.ViewHolder holder, final int position) {
        //to log which item is failed
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(items.get(position).getName());
        holder.email.setText(items.get(position).getEmail());
        holder.repNo.setText(items.get(position).getRepNo());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, repNo;
        RelativeLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            repNo = itemView.findViewById(R.id.repsNo);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}