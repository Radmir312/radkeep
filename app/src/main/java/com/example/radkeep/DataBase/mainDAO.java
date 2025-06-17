package com.example.radkeep.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.radkeep.Models.Notes;
import com.example.radkeep.Models.User;

import java.util.List;

@Dao

public interface mainDAO {


    @Insert (onConflict = REPLACE)
    void insert (Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List <Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE id = :ID")
    void update (int ID, String title, String notes);

    @Delete
    void delete (Notes notes);
    @Query("UPDATE notes SET pinned = :pin WHERE id = :ID")
    void pin (int ID, boolean pin);

    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

}
