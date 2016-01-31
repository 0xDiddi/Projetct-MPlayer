package theultimatehose.projectmplayer.adapter;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import theultimatehose.projectmplayer.R;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    private static String[] titles;
    private static int[] icons;

    private static RelativeLayout[] backgrounds;
    static boolean selected = false;

    static Activity context;

    public MenuRecyclerAdapter(String[] content, int[] img, Activity activity) {
        titles = content;
        context = activity;
        icons = img;
        backgrounds = new RelativeLayout[content.length];
    }

    @Override
    public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.drawer_item_text, parent, false);
            return new ViewHolder(v, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MenuRecyclerAdapter.ViewHolder holder, int position) {
        if (holder.holderId == TYPE_ITEM) {
            holder.textView.setText(titles[position]);
            holder.imageView.setImageResource(icons[position]);
            backgrounds[position] = (RelativeLayout) holder.textView.getParent();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderId;

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.drawer_item_text_text);
                imageView = (ImageView) itemView.findViewById(R.id.drawer_item_text_image);
                holderId = TYPE_ITEM;
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
                if (!selected) {
                    selected = true;
                    RelativeLayout parent = (RelativeLayout) textView.getParent();
                    parent.setBackgroundResource(R.color.colorMedium);
                    parent.setElevation(10);

                    FrameLayout layout = (FrameLayout) context.findViewById(R.id.content_frame);
                    layout.removeAllViews();
                    layout.addView(LayoutInflater.from(context).inflate(R.layout.player, layout, false));
                }
            }
        }

        @Override
        public void onClick(View v) {
            String identifier = (String) textView.getText();

            if (identifier.equals(context.getString(R.string.drw_player))) {
                FrameLayout layout = (FrameLayout) context.findViewById(R.id.content_frame);
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(context).inflate(R.layout.player, layout, false));
            } else if (identifier.equals(context.getString(R.string.drw_lists))) {
                FrameLayout layout = (FrameLayout) context.findViewById(R.id.content_frame);
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(context).inflate(R.layout.playlists, layout, false));
            } else if (identifier.equals(context.getString(R.string.drw_songs))) {
                FrameLayout layout = (FrameLayout) context.findViewById(R.id.content_frame);
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(context).inflate(R.layout.songs, layout, false));

                RecyclerView recView = (RecyclerView) context.findViewById(R.id.songs_recyvclerview);
                recView.setHasFixedSize(true);

                SongListAdapter adapter = new SongListAdapter(context);
                recView.setAdapter(adapter);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recView.setLayoutManager(layoutManager);
            }

            for (RelativeLayout layout : backgrounds) {
                if (layout != null) {
                    layout.setElevation(0);
                    layout.setBackgroundResource(R.color.colorDark);
                }
            }

            RelativeLayout parent = (RelativeLayout) textView.getParent();
            parent.setBackgroundResource(R.color.colorMedium);

            DrawerLayout drawer = (DrawerLayout) context.findViewById(R.id.drawer_parent);
            drawer.closeDrawers();

        }
    }

}
