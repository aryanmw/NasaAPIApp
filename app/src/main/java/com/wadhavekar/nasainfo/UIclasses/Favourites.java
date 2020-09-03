package com.wadhavekar.nasainfo.UIclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.nasainfo.DatabaseHandler.DatabaseManager;
import com.wadhavekar.nasainfo.R;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    DatabaseManager myDb;
    RecyclerView fav;
    FavouritesObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        myDb = new DatabaseManager(this);
        fav = findViewById(R.id.fav_recycler_view);
        fav.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ArrayList<FavouritesObject> myList = new ArrayList<>();

        Cursor data = myDb.retrieveData();
        int numRows = data.getCount();
        if (numRows == 0){
            Toast.makeText(this, "Add some favourites and then come back :)", Toast.LENGTH_SHORT).show();
        }
        else{
            while (data.moveToNext()){
                obj = new FavouritesObject(data.getString(0),data.getString(2));
                myList.add(obj);
                FavListAdapter adapter = new FavListAdapter(this,myList);
                fav.setAdapter(adapter);
            }

        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_APOD:
                    Intent intent = new Intent(Favourites.this, APOD.class);
                    startActivity(intent);
                    break;

                case R.id.nav_search:
                    Intent intent1 = new Intent(Favourites.this,MainActivity.class);
                    startActivity(intent1);
                    break;



            }
            return true;
        }
    };
}
