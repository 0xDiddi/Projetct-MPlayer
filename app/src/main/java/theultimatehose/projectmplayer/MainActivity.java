package theultimatehose.projectmplayer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import theultimatehose.projectmplayer.adapter.MenuRecyclerAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorMedium));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        setContentView(R.layout.drawer_parent);

        RecyclerView recView;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;

        String[] entries = getResources().getStringArray(R.array.menu_drawer_items);
        int[] icons = new int[] {R.drawable.ic_play, R.drawable.ic_playlists, R.drawable.ic_songs};

        recView = (RecyclerView)findViewById(R.id.menu_recycler);
        recView.setHasFixedSize(true);

        adapter = new MenuRecyclerAdapter(entries, icons, this);
        recView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(layoutManager);

    }
}
