package ca.georgebrown.mobile.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ca.georgebrown.mobile.model.Restaurant;



@Database(entities = {Restaurant.class}, version = 1)
public abstract class DB extends RoomDatabase {

    private static DB instance;

    public abstract RestaurantDao restaurantDao();




    //Singleton AppDatabase class, if instance is null,initialize it
    public static DB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DB.class, "app-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    //Use this to dereference the database object within the Singleton, to clean up references and avoid leaks
    public static void destroyInstance(){
        instance = null;
    }

}
