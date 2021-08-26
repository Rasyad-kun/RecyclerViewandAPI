package com.example.recyclerviewandapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    private Context mContext;
    private ArrayList<Model> mLocalList;

    //Detail Activity / Callback / OnItemClickListener
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //RecyclerView
    public LocalAdapter(Context context, ArrayList<Model> localList) {
        this.mContext = context;
        this.mLocalList = localList;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        holder.mTextViewTitle.setText(mLocalList.get(position).getmTitle());
        holder.mTextviewGenre.setText("Genre : " + mLocalList.get(position).getmGenre());
        Picasso.get()
                .load(mLocalList.get(position).getmImage())
                .fit()
                .centerInside()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mLocalList.size();
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle, mTextViewDesc, mTextviewGenre;
        public ImageView mImageView;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_title);
            mTextviewGenre = itemView.findViewById(R.id.text_genre);
            mImageView = itemView.findViewById(R.id.image_view);

            //Detail Activity / Callback / OnItemClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
