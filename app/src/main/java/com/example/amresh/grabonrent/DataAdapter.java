package com.example.amresh.grabonrent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by amresh on 29-03-2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Products> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView productPic;

        public MyViewHolder(View view) {
            super(view);
            productPic=(ImageView) view.findViewById(R.id.ivProduct);
            title = (TextView) view.findViewById(R.id.tvProduct);
        }
    }


    public DataAdapter(List<Products> productList) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products product = productList.get(position);
        holder.title.setText(product.getText());
        makeImageRequest(product.getImageUrl(), holder.productPic);
        //holder.productPic.setImageResource(product.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    private void makeImageRequest(String URL, final ImageView mv) {
        ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
		imageLoader.get(URL, new ImageLoader.ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//Log.e(TAG, "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					mv.setImageBitmap(response.getBitmap());
				}
			}
		});

        imageLoader.get(URL, ImageLoader.getImageListener(
                mv, R.drawable.ico_loading, R.drawable.ico_error));

        Cache cache = VolleyController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
