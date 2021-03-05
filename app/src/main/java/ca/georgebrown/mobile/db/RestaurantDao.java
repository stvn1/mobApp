package ca.georgebrown.mobile.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

import ca.georgebrown.mobile.model.Restaurant;


@Dao
public interface RestaurantDao {

    //Receive List to insert
    @Insert
    void insertAll(List<Restaurant> restaurants);

    //Receive one or more individual items to insert
    @Insert
    void insertAll(Restaurant... restaurants);

    //Returns the number of rows in the restaurant table
    @Query("SELECT COUNT(*) from restaurant")
    int countRestaurants();


    @Query("SELECT * FROM restaurant ORDER BY name")
    List<Restaurant> getAll();

    @Query("SELECT * FROM restaurant WHERE name = :name")
    Restaurant findByName(String name);

    @Query("UPDATE restaurant SET name =:name, address= :address, description = :description, tags = :tag , phone =:phone, rating = :rating  WHERE name = :resturantName")
    void updateResturant(String name, String address, String description, String tag, String phone, float rating, String resturantName);


    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurant WHERE name =:name")
    void deleteResturant(String name);

    @Query("SELECT * FROM restaurant WHERE tags = :tag")
    List<Restaurant> tagFilter(String tag);
}