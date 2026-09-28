package com.unir.appcamil.room;

import androidx.room3.Dao;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.Query;

import java.util.List;

@Dao
public interface ReviewDAO {
    @Query("SELECT * FROM local_reviews")
    List<Review> obterTodas();
    @Insert
    void inserir(Review review);
    @Delete
    void deletar(Review review);
}
