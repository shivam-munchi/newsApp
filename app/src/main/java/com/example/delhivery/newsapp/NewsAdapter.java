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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{


    public NewsAdapter(NewsAdapterOnClickHandler clickHandler) {
        Log.d("click","a");
        mClickHandler = clickHandler;
    }


    private NewsResp mNewsData;

    private final NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(ArticleStructure article);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mNewsTitle;

        public final ImageView mNewsImage;

        public NewsAdapterViewHolder(View view) {
            super(view);
            mNewsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            mNewsImage = (ImageView) view.findViewById(R.id.imageViewNews);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            ArticleStructure articleStructure = mNewsData.getArticles().get(pos);
            mClickHandler.onClick(articleStructure);
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        String title = mNewsData.getArticles().get(position).getTitle();
        holder.mNewsTitle.setText(title);
        Picasso.get().load(mNewsData.getArticles().get(position).getUrlToImage()).resize(150,100).into(holder.mNewsImage);

    }

    @Override
    public int getItemCount() {
        if(mNewsData==null)
            return 0;
        return mNewsData.getArticles().size();
    }

    public void setNewsData(NewsResp newsData) {
        mNewsData = newsData;
        notifyDataSetChanged();
    }


}
