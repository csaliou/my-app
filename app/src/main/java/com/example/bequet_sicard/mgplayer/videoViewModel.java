package com.example.bequet_sicard.mgplayer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class videoViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<Integer> myCurrentPosition;

    public MutableLiveData<Integer> getCurrentPosition() {
        if(myCurrentPosition == null) {
            myCurrentPosition = new MutableLiveData<Integer>();
        }
        return myCurrentPosition;
    }
}
