package com.wadhavekar.nasainfo.UIclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.nasainfo.ModelObjects.AssetEndPoint.AssetResponse;
import com.wadhavekar.nasainfo.R;
import com.wadhavekar.nasainfo.api.ServiceAsset;
import com.wadhavekar.nasainfo.api.client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetImage extends AppCompatActivity {

    ImageView assetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_image);
        assetImage = findViewById(R.id.iv_asset);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        String nasa_id = getIntent().getStringExtra("nasa_id");
        //Toast.makeText(this, nasa_id, Toast.LENGTH_LONG).show();

        try {
            client client = new client();
            ServiceAsset service = client.getClient().create(ServiceAsset.class);
            Call<AssetResponse> call = service.getAssetResponse("The Journeys of Apollo");
            call.enqueue(new Callback<AssetResponse>() {
                @Override
                public void onResponse(Call<AssetResponse> call, Response<AssetResponse> response) {
                    String finalUrl = response.body().getAssetCollection().getAssetItemList().get(0).getHref();
                    for (int i = 0 ; i < response.body().getAssetCollection().getAssetItemList().size(); i++){
                        String imageUrl = response.body().getAssetCollection().getAssetItemList().get(i).getHref();
                        String lastChar = imageUrl.substring(imageUrl.length() - 1);

                        if (lastChar.equals("g")){
                            finalUrl = imageUrl;
                            break;
                        }

                    }
                    Glide.with(AssetImage.this).load("https" + finalUrl.substring(4)).placeholder(R.drawable.progress_animation).into(assetImage);
                }

                @Override
                public void onFailure(Call<AssetResponse> call, Throwable t) {
                    Toast.makeText(AssetImage.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Catch block" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_APOD:
                    Intent intent = new Intent(AssetImage.this, APOD.class);
                    startActivity(intent);
                    break;

                case R.id.nav_search:
                    Intent intent1 = new Intent(AssetImage.this,MainActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.nav_fav:
                    Intent intent2 = new Intent(AssetImage.this,Favourites.class);
                    startActivity(intent2);
                    break;


            }
            return true;
        }
    };
}
