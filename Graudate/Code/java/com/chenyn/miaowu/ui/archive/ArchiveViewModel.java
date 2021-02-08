package com.chenyn.miaowu.ui.archive;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chenyn.miaowu.data.pet.Pet;
import com.chenyn.miaowu.data.pet.PetRepository;

import java.util.List;

public class ArchiveViewModel extends ViewModel {

    private PetRepository mPetRepository;
    // Using LiveData and caching what getAllPosts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // - the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Pet>> mAllPets;

    public ArchiveViewModel(Application application) {
        super();
        mPetRepository = new PetRepository(application);
        mAllPets = mPetRepository.getAllPets();
    }

    public LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }

    public void insertPet(Pet pet) {
        mPetRepository.insertPet(pet);
    }

    public void deletePet(Pet pet) {
        mPetRepository.deletePet(pet);
    }
}