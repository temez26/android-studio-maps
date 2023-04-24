package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // this should be replaced by .newInstance()...
        FragmentMaps fragmentMaps = new FragmentMaps();
        // 2a) Let's initially replace a layout (for date & time) in parent activity


        // 2b) Let's initially replace a layout (for google maps)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment2,fragmentMaps) // the replacement...
                // this will add to android device back stack but not good idea
                // because we are about to start the app...
                //.addToBackStack(null) // "global" unnamed backstack
                .commit(); // and finally commit is needed

        // 3) let's create onclick events for the buttons


    }

}