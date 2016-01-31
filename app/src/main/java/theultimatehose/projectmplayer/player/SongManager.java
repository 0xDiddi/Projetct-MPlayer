package theultimatehose.projectmplayer.player;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SongManager {

    public static ArrayList<HashMap<String, String>> getSongs() {
        File home = Environment.getExternalStorageDirectory();

        return searchRecursive(home);
    }

    public static ArrayList<HashMap<String, String>> searchRecursive(File parent) {
        ArrayList<HashMap<String, String>> songlist = new ArrayList<>(1000);

        if (parent.list().length > 0) {
            for (File f : parent.listFiles()) {
                if (f.isDirectory()) {
                    if (!isDirNoMedia(f)) {
                        songlist.addAll(searchRecursive(f));
                    }
                } else {
                    if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3")) {
                        HashMap<String, String> song = new HashMap<>();
                        song.put("songName", f.getName().substring(0, f.getName().length()-4));
                        song.put("songPath", f.getPath());

                        songlist.add(song);
                    }
                }
            }
        }

        return songlist;
    }

    public static boolean isDirNoMedia(File dir) {
        for (File f : dir.listFiles()) {
            if (f.getName().equals(".nomedia"))
                return true;
        }
        return false;
    }

    public static String getSongArtist(String path) {
        String tag = getSongTag(path);
        if (tag.substring(0, 3).equals("TAG")) {
            return tag.substring(33, 62);
        }
        return "";
    }

    public static String getSongName(String path) {
        String tag = getSongTag(path);
        if (tag.substring(0, 3).equals("TAG")) {
            return tag.substring(3, 32);
        }
        return "";
    }

    public static int getSongDuration(String path, Activity context) {
        try {
            MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
            return mp.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getSongTag(String path) {
        try {
            File f = new File(path);
            FileInputStream input = new FileInputStream(f);
            input.skip(f.length() - 128);
            byte[] last128 = new byte[128];
            input.read(last128);
            return new String(last128);

        } catch (Exception ignored) {}
        return "";
    }

}
