package com.shariaty.melobit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.myViewHolder> {
    private final List<MusicList> list;
    private final Context context;

    public MusicAdapter(List<MusicList> list, Context context) {
        this.list  = list;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.myViewHolder holder, int position) {
        MusicList list2 = list.get(position);

        if(list2.isPlaying())
        {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        }
        else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rootLayout;
        private final TextView title;
        private final TextView artist;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout=itemView.findViewById(R.id.rootLayout);
            title=itemView.findViewById(R.id.musicTitle);
            artist=itemView.findViewById(R.id.musicArtist);

        }
    }
}
