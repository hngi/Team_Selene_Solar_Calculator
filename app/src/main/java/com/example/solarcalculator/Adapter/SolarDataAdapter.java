package com.example.solarcalculator.Adapter;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarcalculator.R;

import java.util.ArrayList;
import java.util.List;

import com.example.solarcalculator.Model.SolarCalData;


public class SolarDataAdapter extends RecyclerView.Adapter<SolarDataAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SolarCalData> dataList;

    private DataListListener listListener;

    public SolarDataAdapter(ArrayList<SolarCalData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public SolarDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SolarDataAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(SolarDataAdapter.ViewHolder holder, int position) {
        SolarCalData sdevice = dataList.get(position);
        holder.Appliance_View.setText(sdevice.getDeviceName());
        holder.Qty_View.setText(String.valueOf(sdevice.getQuantity()));
        holder.Amps_View.setText(String.valueOf(sdevice.getAmps()));
        holder.Volts_View.setText(String.valueOf(sdevice.getVoltage()));
        holder.Hrs_View.setText(String.valueOf(sdevice.getHoursUsedDaily()));
        holder.Watts_View.setText(String.valueOf(sdevice.getWattage()));

        if(position == 0){
            //This hack requires avoiding calculating first data in list, BottomSheet will not calculate first data
            holder.Appliance_View.setText("Appliance");
            holder.Qty_View.setText("Qty");
            holder.Amps_View.setText("Amps");
            holder.Volts_View.setText("Volts");
            holder.Hrs_View.setText("Hrs/Day");
            holder.Watts_View.setText("Watts");

        }

        /*interface implementation to observe list for calculation fab to either display or not
        ViewModel is a newer better alternative too*/
        if(dataList.size()>1){
            listListener.showFab();
        } else {
            listListener.hideFab();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(SolarCalData data){
        dataList.add(data);
        notifyDataSetChanged();
    }

    public void modifyData(SolarCalData editedData, int oldDataPosition){
        dataList.remove(oldDataPosition);
        dataList.add(oldDataPosition,editedData);
        notifyDataSetChanged();
    }

    public List<SolarCalData> getAllData(){
        return dataList;
    }

    public void removeDataAtPosition(int position){
        dataList.remove(position);
        notifyDataSetChanged();
    }

    public SolarCalData getDataAtPosition(int position){
        return dataList.get(position);
    }

    public int clearAllData() {
        int size=dataList.size();
        dataList.clear();
        notifyDataSetChanged();
        return size-1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Appliance_View;
        private TextView Qty_View;
        private TextView Amps_View;
        private TextView Volts_View;
        private TextView Hrs_View;
        private TextView Watts_View;

        private ViewHolder(View itemView) {
            super(itemView);
            Appliance_View = itemView.findViewById(R.id.appliance_view);
            Qty_View = itemView.findViewById(R.id.qty_view);
            Amps_View = itemView.findViewById(R.id.amps_view);
            Volts_View = itemView.findViewById(R.id.volts_view);
            Hrs_View = itemView.findViewById(R.id.hrs_view);
            Watts_View = itemView.findViewById(R.id.watts_view);

            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.setHeaderTitle("Choose an option");
                    if(getAdapterPosition()!=0){ //if statement done because of first fake item in datalist
                        contextMenu.add(getAdapterPosition(), 112, 1, "Edit");
                        contextMenu.add(getAdapterPosition(), 113, 2, "Delete");
                    }

                }
            });
        }
    }



    public interface DataListListener{
        void showFab();
        void hideFab();
    }

    public void setOnDataListListener(DataListListener listListener){
        this.listListener=listListener;
    }
}