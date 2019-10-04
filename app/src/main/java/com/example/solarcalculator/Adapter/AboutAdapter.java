package com.example.solarcalculator.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarcalculator.Model.Developer;
import com.example.solarcalculator.R;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private final Context context;
    private List<Developer> items;

    public AboutAdapter(List<Developer> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public AboutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list, parent, false);
        return new AboutAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AboutAdapter.ViewHolder holder, final int position) {
        final Developer dev = items.get(position);
        holder.mPhoto.setImageResource(dev.getmImage());
        holder.mName.setText(dev.getmName());
        holder.mTitle.setText(dev.getmTitle());
        holder.mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runClick(dev.getmFaceBookUrl());
            }
        });
        holder.mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runClick(dev.getmTwitterUrl());
            }
        });
        holder.mGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runClick(dev.getmGitHubUrl());
            }
        });
        holder.mLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runClick(dev.getmLinkedInUrl());
            }
        });

    }

    private void runClick(String data) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(data));
        context.startActivity(browserIntent);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPhoto;
        private TextView mName;
        private TextView mTitle;
        private ImageView mFacebook;
        private ImageView mTwitter;
        private ImageView mGithub;
        private ImageView mLinkedIn;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.main_logo);
            mName = itemView.findViewById(R.id.name_view);
            mTitle = itemView.findViewById(R.id.title);
            mFacebook = itemView.findViewById(R.id.id_facebook);
            mTwitter = itemView.findViewById(R.id.id_twitter);
            mGithub = itemView.findViewById(R.id.id_github);
            mLinkedIn = itemView.findViewById(R.id.id_linkedin);
        }
    }
}