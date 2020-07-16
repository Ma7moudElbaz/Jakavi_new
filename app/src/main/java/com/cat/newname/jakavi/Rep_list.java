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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cat.newname.R;
import com.cat.newname.adapters.FLM_adapter;
import com.cat.newname.adapters.Rep_adapter;
import com.cat.newname.login.Login;
import com.cat.newname.model_items.FLM_item;
import com.cat.newname.model_items.Rep_item;
import com.cat.newname.network_interface.ServiceInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Rep_list extends AppCompatActivity {

    String url;
    Retrofit retrofit;
    ServiceInterface myInterface;
    private ProgressDialog dialog;

    ArrayList<Rep_item> reps = new ArrayList<>();
    Rep_adapter adapter;
    RecyclerView recyclerView;
    TextView logout;
    ImageView back;

    SharedPreferences shared;
    int flmId ;

    TextView totalTargettxt;
    int totalscore = 0, totaltarget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_list);

        shared = getSharedPreferences("id", Context.MODE_PRIVATE);
        flmId = shared.getInt("flmId", 0);

        logout = findViewById(R.id.logout);
        back = findViewById(R.id.back);
        totalTargettxt = findViewById(R.id.totalTarget);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please, Wait");
        dialog.setCancelable(false);
        url = getResources().getString(R.string.services_url);
        retrofit = new Retrofit.Builder()
                .baseUrl(url).
                        addConverterFactory(GsonConverterFactory.create()).build();
        myInterface = retrofit.create(ServiceInterface.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        initRecyclerView();
        loadReps();
    }

    public void loadReps() {
        myInterface.getReps(flmId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    JSONArray repsArray = responseObject.getJSONArray("data");

                    setRepsList(repsArray);

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

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.repsRecycler);
        adapter = new Rep_adapter(this, reps);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void setRepsList(JSONArray list) throws JSONException {

        for (int i = 0; i < list.length(); i++) {
            JSONObject currentobject = list.getJSONObject(i);
            final int id = currentobject.getInt("id");
            final String name = currentobject.getString("name");
            final String email = currentobject.getString("email");
            final String type = currentobject.getString("type");
            final int scored = currentobject.getInt("score");
            final int target = currentobject.getInt("target");
            final String percentage = currentobject.getString("target");

            totalscore = totalscore+scored;
            totaltarget = totaltarget+target;

            reps.add(new Rep_item(id, name, email, type,scored,target,percentage));
        }
        adapter.notifyDataSetChanged();
        totalTargettxt.setText("Target "+totalscore+" / "+totaltarget);
    }
}