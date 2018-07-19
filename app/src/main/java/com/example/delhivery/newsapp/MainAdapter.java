package com.example.delhivery.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>{


    public MainAdapter(MainAdapterOnClickHandler clickHandler) {
        Log.d("click","a");
        mClickHandler = clickHandler;
    }


    private SourceResp mSourceData;

    private final MainAdapterOnClickHandler mClickHandler;

    public interface MainAdapterOnClickHandler {
        void onClick(Sources src);
    }

    public class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mSourceId;

        public final TextView mSourceName;

        public MainAdapterViewHolder(View view) {
            super(view);
            mSourceId = (TextView) view.findViewById(R.id.tv_src_id);
            mSourceName = (TextView) view.findViewById(R.id.tv_src_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Sources src = mSourceData.getSrc().get(pos);
            mClickHandler.onClick(src);
        }
    }

    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.source_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new MainAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MainAdapterViewHolder holder, int position) {
        String id = mSourceData.getSrc().get(position).getId();
        holder.mSourceId.setText(id);

        String name = mSourceData.getSrc().get(position).getName();
        holder.mSourceName.setText(name);


    }

    @Override
    public int getItemCount() {
        if(mSourceData==null)
            return 0;
        return mSourceData.getSrc().size();
    }

    public void setSourceData(SourceResp srcData) {
        mSourceData = srcData;
        notifyDataSetChanged();
    }


}
