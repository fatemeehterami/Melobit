package com.shariaty.melobit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.myViewHolder> {
    private List<MusicList> list;
    private final Context context;
    private int playingPosition=0;
    private final SongChangeListener songChangeListener;

    public MusicAdapter(List<MusicList> list, Context context) {
        this.list  = list;
        this.context = context;
        this.songChangeListener= ((SongChangeListener) context);
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
            playingPosition = position;
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        }
        else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);


        }

        String generateDuration= String.format(Locale.getDefault(),"%02d:02d%", TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())),
        TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(list2.getDuration())) - TimeUnit.MINUTES.toSeconds(MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration()))));





        holder.title.setText(list2.getTitle());
        holder.artist.setText(list2.getArtist());
        holder.musicDuration.setText(list2.getDuration());


        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(playingPosition).setPlaying(false);
                list2.setPlaying(true);

                songChangeListener.onChangeed(position);
                notifyDataSetChanged();


            }
        });



    }

    public void updateList(List<MusicList>list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rootLayout;
        private final TextView title;
        private final TextView artist;
        private final TextView musicDuration;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout=itemView.findViewById(R.id.rootLayout);
            title=itemView.findViewById(R.id.musicTitle);
            artist=itemView.findViewById(R.id.musicArtist);
            musicDuration =itemView.findViewById(R.id.musicDuration);

        }
    }
}
