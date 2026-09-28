package com.unir.appcamil.room;

import androidx.room3.ColumnInfo;
import androidx.room3.Entity;
import androidx.room3.PrimaryKey;

@Entity(tableName = "local_reviews")
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "titulo")
    public String titulo;
    @ColumnInfo(name = "nota")
    public float nota;
    @ColumnInfo(name = "review")
    public String review;
}
