package com.wadhavekar.nasainfo.UIclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.wadhavekar.nasainfo.DatabaseHandler.DatabaseManager;
import com.wadhavekar.nasainfo.ModelObjects.APODEndPoint.APODResponse;
import com.wadhavekar.nasainfo.R;
import com.wadhavekar.nasainfo.api.ServiceAPOD;
import com.wadhavekar.nasainfo.api.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APOD extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView title;
    ImageView urlAPOD;
    Button getAPOD;
    TextView tvDate;
    WebView videoUrl;
    MediaController mediaController;
    RelativeLayout open;
    DatabaseManager db;
    CardView addToFavs,unfav;
    String url;
    String dateSet;

    private static final String API_KEY = "HlLkMF7B0nLwQ1LUOeIBhYxKzbnXyPbMikdBd8jH";

    public static final String SHARED_PREF = "APOD";
    public static final String SET_DATE = "Set Date";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        title = findViewById(R.id.tv_title);
        urlAPOD = findViewById(R.id.iv_url);
        open = findViewById(R.id.layout_open);
        tvDate = findViewById(R.id.tv_date);
        //getAPOD = findViewById(R.id.button_getAPOD);
        videoUrl = findViewById(R.id.vv_video);
        videoUrl.getSettings().setJavaScriptEnabled(true);
        videoUrl.setWebChromeClient(new WebChromeClient());
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        db= new DatabaseManager(APOD.this);
        addToFavs = findViewById(R.id.cv_addToFavs);
        unfav = findViewById(R.id.cv_unFav);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        tvDate.setText(dtf.format(now));

        if (!checkEntryInDatabase(tvDate.getText().toString())){
            addToFavs.setVisibility(View.INVISIBLE);
            unfav.setVisibility(View.VISIBLE);
        }

        addToFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addData(tvDate.getText().toString(),url,title.getText().toString());
                addToFavs.setVisibility(View.INVISIBLE);
                unfav.setVisibility(View.VISIBLE);
                Toast.makeText(APOD.this, "APOD has been added to favourites!", Toast.LENGTH_SHORT).show();
            }
        });

        unfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfav.setVisibility(View.INVISIBLE);
                addToFavs.setVisibility(View.VISIBLE);
                db.deleteFav(tvDate.getText().toString());
                Toast.makeText(APOD.this, "APOD has been removed from favourites!", Toast.LENGTH_SHORT).show();

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            dateSet = extras.getString("date");
            tvDate.setText(dateSet);
        }
        else{
            dateSet = tvDate.getText().toString();
        }



        if (checkConnection()){
            title.setText("");
            loadJSON(dateSet);
        }
        else{
            title.setText("Please check your connection :(");
        }


        mediaController = new MediaController(this);




        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SharedPreferences sp = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(SET_DATE,tvDate.getText().toString());
                    editor.apply();
                DialogFragment datePicker = new DatePickerFragment();

                datePicker.show(getSupportFragmentManager(),"date Picker");
            }
        });

//        getAPOD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//            }
//        });


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        String date;
        Date date1 = c.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");


        Calendar cal = Calendar.getInstance();
        int yearCal = cal.get(Calendar.YEAR);
        int monthCal = cal.get(Calendar.MONTH);
        int dayCal = cal.get(Calendar.DAY_OF_MONTH);
        Date date2 = cal.getTime();


        date = format1.format(date1);

        tvDate.setText(date);

        if (checkConnection()){
            title.setText("");
            loadJSON(date);
            if (checkEntryInDatabase(date)){
                addToFavs.setVisibility(View.VISIBLE);
                unfav.setVisibility(View.INVISIBLE);
            }
            else{
                addToFavs.setVisibility(View.INVISIBLE);
                unfav.setVisibility(View.VISIBLE);
            }
        }
        else{
            title.setText("Please check your connection :(");
        }




    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_search:
                    Intent intent = new Intent(APOD.this, MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_fav:
                    Intent intent1 = new Intent(APOD.this,Favourites.class);
                    startActivity(intent1);
                    break;


            }
            return true;
        }
    };

    private void loadJSON(String date){
        client clientAPOD = new client();
        ServiceAPOD apiService = clientAPOD.getAPOD().create(ServiceAPOD.class);
        Call<APODResponse> call = apiService.getResponse(API_KEY,date);
        call.enqueue(new Callback<APODResponse>() {
            @Override
            public void onResponse(Call<APODResponse> call, Response<APODResponse> responseAPOD) {
                String apodTitle = responseAPOD.body().getTitle();
                title.setText(apodTitle);
                url = responseAPOD.body().getUrl();
                if (responseAPOD.body().getMediaType().equals("video")){
                    videoUrl.setVisibility(View.VISIBLE);
                    urlAPOD.setVisibility(View.INVISIBLE);
                    videoUrl.loadUrl(url);

                }
                else {
                    videoUrl.setVisibility(View.INVISIBLE);
                    urlAPOD.setVisibility(View.VISIBLE);
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext())
                                    .downloader(new OkHttp3Downloader(getApplicationContext()));
                            builder.build().load(url).placeholder(R.drawable.progress_animation).into(urlAPOD);
                    //Glide.with(APOD.this).load(url).placeholder(R.drawable.progress_animation).into(urlAPOD);
                }
            }

            @Override
            public void onFailure(Call<APODResponse> call, Throwable t) {

            }
        });
    }

    public boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEntryInDatabase(String date){
        Cursor data = db.retrieveData();
        int numRows = data.getCount();
        boolean canAdd = true;
        if (numRows == 0){
            //Toast.makeText(this, "No entries added yet", Toast.LENGTH_SHORT).show();
            canAdd = true;

        }
        else{
            while (data.moveToNext()){
                if (date.equals(data.getString(0))){
                    canAdd = false;
                    break;
                }
            }

        }
        return canAdd;

    }
}
