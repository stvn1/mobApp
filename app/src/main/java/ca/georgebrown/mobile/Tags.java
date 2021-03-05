package ca.georgebrown.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.georgebrown.mobile.db.DB;
import ca.georgebrown.mobile.model.Restaurant;

public class Tags extends AppCompatActivity {

    private ArrayAdapter<String> adapter;


    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        db = DB.getInstance(this);
        ArrayList<String> restaurantNames = new ArrayList<String>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String tag = preferences.getString("Tag", "");

        //Get all restaurants
        List<Restaurant> restaurantList = db.restaurantDao().tagFilter(tag);


        for(Restaurant restaurant :restaurantList){
            restaurantNames.add(restaurant.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantNames);

        ListView listView = findViewById(R.id.listRestuarantTagList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectionItem = (String)parent.getItemAtPosition(position);

                //Add selected resturant to Preferences
                SharedPreferences selectedTemp = getSharedPreferences("selected", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedTemp.edit();
                editor.putString("resturantSelected", selectionItem);
                editor.apply();

                Intent intent = new Intent(view.getContext(), RestaurantDetails.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
