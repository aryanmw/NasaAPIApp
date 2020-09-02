package com.wadhavekar.nasainfo.UIclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint.Items;
import com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint.SearchResponse;
import com.wadhavekar.nasainfo.R;
import com.wadhavekar.nasainfo.api.client;
import com.wadhavekar.nasainfo.api.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView searchRv;
    SearchRecyclerViewAdapter adapter;
    TextView error;
    SearchView sv;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        error = findViewById(R.id.error);
        //sv = findViewById(R.id.sv_search);
        et = findViewById(R.id.et_search);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);




        searchRv = findViewById(R.id.rv_search);
        searchRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (checkConnection()) {
                    try {
                        client client = new client();
                        service apiService = client.getClient().create(service.class);
                        if (editable.toString().equals("")){

                        }
                        else{
                            Call<SearchResponse> call = apiService.getItems(editable.toString());
                            call.enqueue(new Callback<SearchResponse>() {
                                @Override
                                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                                    try {
                                        List<Items> itemList = response.body().getCollection().getItems();
                                        if (itemList.size() > 0) {
                                            adapter = new SearchRecyclerViewAdapter(MainActivity.this, itemList);
                                            searchRv.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            searchRv.smoothScrollToPosition(0);
                                            error.setText("");
                                        }
                                        else{
                                            error.setText("No results match your query");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }

                                @Override
                                public void onFailure(Call<SearchResponse> call, Throwable t) {
                                    t.getMessage();
                                    Log.i("Error-----",t.getMessage());
                                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                    error.setText(t.getMessage());

                                }
                            });
                        }

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                else{
                    error.setText("Check your connection :(");
                }

            }
        });






    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_APOD:
                    Intent intent = new Intent(MainActivity.this, APOD.class);
                    startActivity(intent);
                    break;



            }
            return true;
        }
    };

    public boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
