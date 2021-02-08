package com.chenyn.miaowu.data.pet;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PetDao {
    @Insert
    void insertPet(Pet pet);

    @Delete()
    void deletePet(Pet pet);

    @Query("DELETE FROM pet_table WHERE pet_name = :petName")
    void deletePetById(int petName);

    @Query("DELETE FROM pet_table")
    void deleteAllPets();

    @Query("SELECT * FROM pet_table")
    LiveData<List<Pet>> getAllPets();
}
