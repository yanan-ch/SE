package com.chenyn.miaowu.data.pet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_table")
public class Pet {
    @PrimaryKey
    @ColumnInfo(name = "pet_profile_url")
    private String petProfileUrl;

    @NonNull
    @ColumnInfo(name = "pet_name")
    private String petName;

    @ColumnInfo(name = "pet_gender")
    private String petGender;

    @ColumnInfo(name = "pet_birth")
    private String petBirth;

    @ColumnInfo(name = "pet_weight")
    private String petWeight;

    public Pet(String petProfileUrl, String petName,
               String petGender, String petBirth, String petWeight) {
        this.petProfileUrl = petProfileUrl;
        this.petName = petName;
        this.petGender = petGender;
        this.petBirth = petBirth;
        this.petWeight = petWeight;
    }

    public void setPetProfileUrl(String petProfileUrl) {
        this.petProfileUrl = petProfileUrl;
    }

    public String getPetProfileUrl() {
        return petProfileUrl;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetBirth(String petBirth) {
        this.petBirth = petBirth;
    }

    public String getPetBirth() {
        return petBirth;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getPetWeight() {
        return petWeight;
    }
}
