package com.cat.newname.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cat.newname.R;
import com.cat.newname.jakavi.JakaviHome;
import com.cat.newname.model_items.FLM_item;
import com.cat.newname.model_items.Rep_item;
import com.cat.newname.revolade.RevoladeHome;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Rep_adapter extends RecyclerView.Adapter<Rep_adapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<Rep_item> items;

    private Context mContext;

    public Rep_adapter(Context context, ArrayList<Rep_item> items) {

        this.mContext = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Rep_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rep, parent, false);
        Rep_adapter.ViewHolder holder = new Rep_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Rep_adapter.ViewHolder holder, final int position) {
        //to log which item is failed
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(items.get(position).getName());
        holder.email.setText(items.get(position).getEmail());
        holder.productName.setText(items.get(position).getType());
        holder.target.setText(items.get(position).getScored()+" / "+items.get(position).getTarget());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shared = mContext.getSharedPreferences("id", Context.MODE_PRIVATE);;
                SharedPreferences.Editor myEdit = shared.edit();
                myEdit.putInt("repId", items.get(position).getId());
                myEdit.putString("name", items.get(position).getName());
                myEdit.putString("email", items.get(position).getEmail());
                myEdit.putString("type", items.get(position).getType());
                myEdit.putInt("scored", items.get(position).getScored());
                myEdit.putInt("target", items.get(position).getTarget());
                myEdit.putString("percentage", items.get(position).getPercentage());

                myEdit.commit();

                if (items.get(position).getType().equals("jakavi")) {
                    Intent i = new Intent(mContext, JakaviHome.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, RevoladeHome.class);
                    mContext.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, productName,target;
        RelativeLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            productName = itemView.findViewById(R.id.product);
            target = itemView.findViewById(R.id.target);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}