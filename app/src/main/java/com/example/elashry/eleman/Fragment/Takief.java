package com.example.elashry.eleman.Fragment;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.elashry.eleman.Adapter.Washer_Adapter;
import com.example.elashry.eleman.Controller;
import com.example.elashry.eleman.Model.Product_Model;
import com.example.elashry.eleman.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Takief extends Fragment {
    private RecyclerView mrRecyclerView;
    private Context mContext;
    private List<Product_Model> pro_List;
    private final String products_url ="https://semicolonsoft.com/clients/emc/api/find/products";
    private TextView nopro_txt;
    private LinearLayout progBar_container;
    private ProgressBar prog_bar;
    private SwipeRefreshLayout mRefreshLayout;

    public Takief() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.takief,container,false);
        init_View(view);
        Get_proData(products_url);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Get_proData(products_url);
            }
        });
        return view;
    }
    private void Get_proData(String products_url) {

        JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(products_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("data",response.toString());
                        JSONObject object;
                        pro_List = new ArrayList<>();

                        for (int index=0;index<response.length();index++)
                        {

                            try {
                                object =response.getJSONObject(index);
                                Toast.makeText(mContext,object.get("product_title").toString()+"\n"+object.get("cat_id_fk").toString(), Toast.LENGTH_SHORT).show();

                                if (object.get("cat_id_fk").toString().equals("6"))
                                {
                                    pro_List.add(new Product_Model(object.get("cat_id_fk").toString(),object.get("product_title").toString(),object.get("product_photo").toString()));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (pro_List.size()>0)
                        {
                            Washer_Adapter adapter = new Washer_Adapter(mContext,pro_List);
                            mrRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mrRecyclerView.setVisibility(View.VISIBLE);
                            progBar_container.setVisibility(View.GONE);
                            nopro_txt.setVisibility(View.GONE);
                            mRefreshLayout.setRefreshing(false);
                        }
                        else if (pro_List.size()==0)
                        {
                            nopro_txt.setVisibility(View.VISIBLE);
                            mrRecyclerView.setVisibility(View.GONE);
                            progBar_container.setVisibility(View.GONE);
                            mRefreshLayout.setRefreshing(false);
                        }

                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("errrrr",error.getMessage()+"");
                    }
                }
        );
        Controller.getInstance().addToRequestQueue(mJsonArrayRequest,"json array req");
    }
    private void init_View(View view) {
        mContext =view.getContext();
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swap_refresh);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext,R.color.colorPrimary));

        mrRecyclerView = (RecyclerView) view.findViewById(R.id.takief_recyView);
        mrRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mrRecyclerView.setVisibility(View.GONE);
        prog_bar          = (ProgressBar) view.findViewById(R.id.tk_progBar);
        prog_bar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        progBar_container = (LinearLayout) view.findViewById(R.id.progressBar_container);
        progBar_container.setVisibility(View.VISIBLE);

        nopro_txt         = (TextView) view.findViewById(R.id.nopro_txt);

    }


}
