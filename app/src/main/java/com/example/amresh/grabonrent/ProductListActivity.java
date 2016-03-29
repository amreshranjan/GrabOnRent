package com.example.amresh.grabonrent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListActivity extends Activity {

	private String TAG = ProductListActivity.class.getSimpleName();
	private ProgressDialog pDialog;
	private ArrayList<Products> modelList = new ArrayList<>();
	private RecyclerView recyclerView;
	private DataAdapter mDataAdapter;
	private String[] urlList={"http://www.thinkrent.in/products/show","http://www.thinkrent.in/products/show/?page=2"};


	private String  tag_json_arry = "jarray_req";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		for(int i=0; i<urlList.length; i++) {

			makeCallToJSONArray(urlList[i]);
		}

	}

	private void showProgressDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}

	private void makeCallToJSONArray(String URL) {
		showProgressDialog();
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						JSONObject obj = null;
						for (int i =0;i<response.length();i++) {
							try {
								obj = response.getJSONObject(i);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							if(obj!=null)
							{
								Products m = null;
								try {
									m = new Products(obj.getString("text"),obj.getString("image"));
								} catch (JSONException e) {
									e.printStackTrace();
								}
								if(m!=null)
									modelList.add(m);
							}
						}
						//msgResponse.setText(response.toString());
						mDataAdapter = new DataAdapter(modelList);
						recyclerView.setAdapter(mDataAdapter);
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				});


		// Adding request to request queue
		VolleyController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


}
