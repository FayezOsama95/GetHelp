package com.am.gethelp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterListNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> items;

    @LayoutRes
    private int layout_id;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(View view, News obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListNews(List<News> items, @LayoutRes int layout_id) {
        this.items = items;
        this.layout_id = layout_id;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public TextView title;

        public TextView subtitle;

        public TextView date;

        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            subtitle = v.findViewById(R.id.subtitle);
            date = v.findViewById(R.id.date);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            News n = items.get(position);
            view.title.setText(n.title);
            view.subtitle.setText(n.subtitle);
            view.date.setText(n.date);
            displayImageOriginal(view.image, n.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener == null) {
                        return;
                    }
                    mOnItemClickListener.onItemClick(view, items.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void displayImageOriginal(ImageView img, @DrawableRes int drawable) {
        try {
            Picasso.get().load(drawable).into(img);
        } catch (Exception e) {
        }
    }

}