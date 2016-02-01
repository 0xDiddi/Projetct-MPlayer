package theultimatehose.projectmplayer.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import theultimatehose.projectmplayer.R;
import theultimatehose.projectmplayer.player.SongManager;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    private static ArrayList<HashMap<String, String>> songList;

    static Activity context;

    public SongListAdapter(Activity activity) {
        context = activity;
        songList = SongManager.getSongs();
    }

    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View v = LayoutInflater.from(context).inflate(R.layout.songs_entry, parent, false);
        return new ViewHolder(v, pos);
    }

    @Override
    public void onBindViewHolder(SongListAdapter.ViewHolder holder, int position) {
        HashMap<String, String> song = songList.get(position);
        String name = SongManager.getSongName(song.get("songPath"));
        if (name != null && !name.equals("") && !name.startsWith(String.valueOf((char)0)))
            holder.txt_name.setText(name);
        else
            holder.txt_name.setText(song.get("songName"));

        String artist = SongManager.getSongArtist(song.get("songPath"));
        if (!Objects.equals(artist, ""))
            holder.txt_artist.setText(artist);
        else
            holder.txt_artist.setText(context.getResources().getText(R.string.song_artist));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView txt_name;
        TextView txt_artist;
        int position;

        public ViewHolder(View itemView, int pos) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.song_list_cover);
            txt_name = (TextView) itemView.findViewById(R.id.song_list_name);
            txt_artist = (TextView) itemView.findViewById(R.id.song_list_artist);
            position = pos;
        }
    }

}
