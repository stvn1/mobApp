package ca.georgebrown.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ca.georgebrown.mobile.db.DB;
import ca.georgebrown.mobile.model.Restaurant;

public class Landing extends AppCompatActivity {

    private DB db;
    private Button gotolist, btnAdd;
    private EditText aName,aAddress,aPhone,aDescription;
    private RatingBar aRating;
    private Spinner spinnerTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        db = DB.getInstance(this);
        gotolist = findViewById(R.id.btnLoadData);
        btnAdd = findViewById(R.id.btnAdd);
        aName = findViewById(R.id.txtAddName);
        aAddress = findViewById(R.id.txtRestuantAddress);
        aPhone = findViewById(R.id.txtAddPhone);
        aRating = findViewById(R.id.rbResturant);
        aDescription = findViewById(R.id.txtAddDescription);
        spinnerTags = findViewById(R.id.spinnerResturantTags);






        SharedPreferences preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String userName = preferences.getString("name", null);
        TextView name = (TextView)findViewById(R.id.txtWelcome);

        if(userName != null) {
            name.setText("Welcome " + userName);
        }

        Toast.makeText(this,"Welcome "+ userName, Toast.LENGTH_LONG).show();

        Spinner spinner = (Spinner) findViewById(R.id.spinnerResturantTags);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Landing.this,R.array.resturant_tags,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //First ask if there are already data in the database, if not, import data
        int restaurantCount = db.restaurantDao().countRestaurants();
        if (restaurantCount == 0){
            //insert into the database
            db.restaurantDao().insertAll(new Restaurant(
                    "Fresii",
                    "77 Wellesley St E, Toronto, ON M4Y 1H5",
                    "Health-minded, counter serve chain offering " +
                            "tossed-to-order salads plus soups, burritos and more",
                    "vegetarian",
                    "(647) 350-7797",
                    4.0f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Adam's Shawarma",
                    "78 Wellesley St E, Toronto, ON M4Y 1H2",
                    "popular local fast-food chain serving shawarma, falafel" +
                            "& other classics until late night",
                    "Late-Night food",
                    "(416) 922-4417",
                    4.1f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Sansotei Ramen",
                    "650 Yonge St, Toronto, ON M4Y 2A6",
                    " Casual restaurant serving modern Japanese" +
                            "ramen dishes & sides in relaxed digs",
                    "Japanese",
                    "(647) 349-3833",
                    4.4f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Le Sélect Bistro",
                    "432 Wellington St W, Toronto, ON M5V 1E3",
                    "Classic french food & a wine list of over " +
                            "1000 bottles with vintage posters lining the walls",
                    "French",
                    "(416) 596-6405",
                    4.5f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Smoke's Poutinerie",
                    "218 Adelaide St W, Toronto, ON M5H 1W7",
                    "Poutine-only pit stop",
                    "Late-night food",
                    "(416) 599-2873",
                    4.1f));
            db.restaurantDao().insertAll(new Restaurant(
                    "McDonald's",
                    "345 Bloor St E, Toronto, ON M4W 3J6",
                    "Classic, long-running fast-food chain known " +
                            "for its burgers, fries and shakes",
                    "Late-night food",
                    "(416) 967-1081",
                    3.6f));

            Log.i("DATABASE-TEST","OnCreate: data inserted");
            Toast.makeText(this, "onCreate: inserted data", Toast.LENGTH_LONG).show();

        } else {
            Log.i("DATABASE-TEST","onCreate: data already exists");
            Toast.makeText(this, "onCreate: data already exists", Toast.LENGTH_LONG).show();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addName,addAddress,addDescription,addTag,addPhone;
                float addRating;

                addName = aName.getText().toString();
                addAddress = aAddress.getText().toString();
                addDescription = aDescription.getText().toString();
                addTag = spinnerTags.getSelectedItem().toString();
                addPhone = aPhone.getText().toString();
                addRating = aRating.getRating();

                db.restaurantDao().insertAll(new Restaurant(
                        addName,
                        addAddress,
                        addDescription,
                        addTag,
                        addPhone,
                        addRating
                ));
                Toast.makeText(Landing.this, "Added to database!", Toast.LENGTH_LONG).show();

                aName.setText("");
                aAddress.setText("");
                aDescription.setText("");
                aPhone.setText("");

            }
        });



        gotolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestaurantList.class);
                startActivity(intent);
                finish();
            }
        });












    }





    protected void onDestroy() {

        DB.destroyInstance();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAddResturant:
                startActivity(new Intent(this, Landing.class));
                finish();
                return true;

            case R.id.itemResturantList:
                startActivity(new Intent(this, RestaurantList.class));
                return true;

            case R.id.itemAboutUs:
                startActivity(new Intent(this, AboutUs.class));
                return true;

            case R.id.itemSplashScreen:
                startActivity(new Intent(this, Splash.class));
                return true;



            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
