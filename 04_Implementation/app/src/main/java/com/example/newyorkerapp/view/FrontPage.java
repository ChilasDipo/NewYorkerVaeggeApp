package com.example.newyorkerapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.newyorkerapp.R;
import com.example.newyorkerapp.model.data.ListOfWallsFromCatalog;
import com.example.newyorkerapp.persistence.FireBaseDAOimpl;
import com.example.newyorkerapp.viewModel.MainActivityViewModel;

public class FrontPage extends AppCompatActivity {
    MainActivityViewModel mMainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getSupportActionBar().hide();
        setContentView(R.layout.activity_front_page);

        //Skaber en Forbindelse til Vores ViewModel og kalder funktionen til at opbygge vores lister med data fra databasen
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.buildingNeedListForApplicationToWork();

       /*Kalder metode som opbygger nogle Arraylister i vores database klasse,
         der bruges til at genmme alt fra navne til priser*/
       FireBaseDAOimpl.buildHashMapsForFetureNameAndPrice();
        /*Vores metode til at opbygge vores væg opbejkter i kataloget bliver kaldt efter 2000 milisekunder
         da den bruger en liste der skal være opbygget i Database klassen, hvis der ikke er et delay
         så ville lisen være tom og der ville komme en nullpointer exception under program kørsel*/
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ListOfWallsFromCatalog.buildListOfWallsFromCatalog();
            }
        }, 2000);

        //Sender os over til den næste Activity i vores program efter 3000 milisekunder
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(FrontPage.this, StartPage.class);
                startActivity(myIntent);
            }
        }, 3000);
    }
}