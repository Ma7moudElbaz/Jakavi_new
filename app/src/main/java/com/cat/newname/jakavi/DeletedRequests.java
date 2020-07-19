package com.cat.newname.jakavi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cat.newname.R;
import com.cat.newname.adapters.Delete_Request_adapter;
import com.cat.newname.model_items.Delete_requerst_item;
import com.cat.newname.network_interface.ServiceInterface;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeletedRequests extends AppCompatActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper{

    String url;
    int flmId;
    Retrofit retrofit;
    ServiceInterface myInterface;
    private ProgressDialog dialog;

    ArrayList<Delete_requerst_item> requests = new ArrayList<>();

    ImageView back;
    RecyclerView recyclerView;

    SharedPreferences shared;

    Delete_Request_adapter adapter;

    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_requests);

        shared = getSharedPreferences("id", Context.MODE_PRIVATE);
        flmId = shared.getInt("flmId", 0);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please, Wait");
        dialog.setCancelable(false);
        url = getResources().getString(R.string.services_url);
        retrofit = new Retrofit.Builder()
                .baseUrl(url).
                        addConverterFactory(GsonConverterFactory.create()).build();
        myInterface = retrofit.create(ServiceInterface.class);
        loadDeletedRequest();
        initRecyclerView();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.patientsRecycler);
        adapter = new Delete_Request_adapter(this, requests);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {

            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        })
                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
                    @Override
                    public void onRowLongClicked(int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.delete) {
                            confirmDeletePatient(requests.get(position).getId());
                            requests.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }




    public void confirmDeletePatient(int patientId) {

        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(flmId));

        myInterface.confirmDeletePatient(patientId, map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
//                    String msg = responseObject.getString("msg");

                    Toast.makeText(getBaseContext(), responseObject.toString(), Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public void loadDeletedRequest() {
        dialog.show();
        myInterface.getDeletedRequests(flmId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    JSONArray requestsArray = responseObject.getJSONArray("data");
                    setRequestsList(requestsArray);
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error Throw", t.toString());
                Log.d("commit Test Throw", t.toString());
                Log.d("Call", t.toString());
                Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void setRequestsList(JSONArray list) {
        try {

            for (int i = 0; i < list.length(); i++) {
                JSONObject currentobject = list.getJSONObject(i);
                final int id = currentobject.getInt("id");
                final String name = currentobject.getString("doctor_name");
                final String dose = currentobject.getString("dose_name");
                final String category = currentobject.getString("category_name");
                final String hospital = currentobject.getString("hospital_name");
                final String date = currentobject.getString("created_at");
                final String productId = currentobject.getString("product_id");
                requests.add(new Delete_requerst_item(id, name, dose, category, hospital, date, productId));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnItemTouchListener(onTouchListener);
    }
}