package ca.georgebrown.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AboutUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

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
