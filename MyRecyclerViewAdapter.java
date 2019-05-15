package com.example.morri.messingaround;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageDescriptions = new ArrayList<>();
    private ArrayList<String> mImagePrices = new ArrayList<>();
    private ArrayList<Bitmap> mImages = new ArrayList<>();
    private ArrayList<Integer> mImageIDs = new ArrayList<>();
    private Context mContext;

    public MyRecyclerViewAdapter(ArrayList<Integer> IDs, ArrayList<String> imageNames, ArrayList<String> imageDescriptions,
                                 ArrayList<String> imagePrices, ArrayList<Bitmap> images, Context context){
        mImageIDs = IDs;
        mImageNames = imageNames;
        mImageDescriptions = imageDescriptions;
        mImagePrices = imagePrices;
        mImages = images;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(mImageNames.get(position));
        holder.imageDescription.setText(mImageDescriptions.get(position));
        holder.imagePrice.setText(mImagePrices.get(position));
        holder.image.setImageBitmap(mImages.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: click on: " + mImageNames.get(position));
                int id = mImageIDs.get(position);
                Intent itemIntent = new Intent(mContext, Item_Activity.class);
                itemIntent.putExtra("itemID", id);
                mContext.startActivity(itemIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        TextView imageDescription;
        TextView imagePrice;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            imageDescription = itemView.findViewById(R.id.image_description);
            imagePrice = itemView.findViewById(R.id.image_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
