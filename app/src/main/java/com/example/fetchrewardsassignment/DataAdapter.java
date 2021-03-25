package com.example.fetchrewardsassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * DataAdapter is the adapter for the RecyclerView
 *
 * @author Justin Mabutas
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<DataModel> dataModels;
    private Context context;

    public DataAdapter(Context context, ArrayList<DataModel> dataModels) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_model_item, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        holder.listIdTV.setText("List ID: " + dataModels.get(position).getListId());
        holder.nameTV.setText("Name: " + dataModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView listIdTV;
        private TextView nameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listIdTV = itemView.findViewById(R.id.data_list_id_tv);
            nameTV = itemView.findViewById(R.id.data_name_tv);
        }
    }
}
