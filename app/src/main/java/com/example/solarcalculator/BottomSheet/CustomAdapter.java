package com.example.solarcalculator.BottomSheet;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarcalculator.R;

import java.util.ArrayList;
import com.example.solarcalculator.Model.SolarCalData;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SolarCalData> items;

    public CustomAdapter(ArrayList<SolarCalData> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CustomAdapter.ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        SolarCalData sdevice = items.get(position);
        holder.Appliance_View.setText(sdevice.getDeviceName());
        holder.Qty_View.setText(String.valueOf(sdevice.getQuantity()));
        holder.Amps_View.setText(String.valueOf(sdevice.getAmps()));
        holder.Volts_View.setText(String.valueOf(sdevice.getVoltage()));
        holder.Hrs_View.setText(String.valueOf(sdevice.getHoursUsedDaily()));
        holder.Watts_View.setText(String.valueOf(sdevice.getWattage()));

        if(position == 0){
            //Fix First Column;
            holder.Appliance_View.setText("Appliance");
            holder.Qty_View.setText("Qty");
            holder.Amps_View.setText("Amps");
            holder.Volts_View.setText("Volts");
            holder.Hrs_View.setText("Hrs/Day");
            holder.Watts_View.setText("Watts");

            //Fix Font
            Typeface typeface = context.getResources().getFont(R.font.opensans_extrabold);
            holder.Appliance_View.setTypeface(typeface);
            holder.Qty_View.setTypeface(typeface);
            holder.Amps_View.setTypeface(typeface);
            holder.Volts_View.setTypeface(typeface);
            holder.Hrs_View.setTypeface(typeface);
            holder.Watts_View.setTypeface(typeface);
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Appliance_View;
        public TextView Qty_View;
        public TextView Amps_View;
        public TextView Volts_View;
        public TextView Hrs_View;
        public TextView Watts_View;

        public ViewHolder(View v) {
            super(v);
            Appliance_View = v.findViewById(R.id.appliance_view);
            Qty_View = v.findViewById(R.id.qty_view);
            Amps_View = v.findViewById(R.id.amps_view);
            Volts_View = v.findViewById(R.id.volts_view);
            Hrs_View = v.findViewById(R.id.hrs_view);
            Watts_View = v.findViewById(R.id.watts_view);
        }
    }
}