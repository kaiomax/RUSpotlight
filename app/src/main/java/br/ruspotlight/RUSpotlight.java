package br.ruspotlight;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class RUSpotlight extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
