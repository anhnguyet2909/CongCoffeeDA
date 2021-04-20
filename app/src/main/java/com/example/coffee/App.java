package com.example.coffee;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", R.string.sender);
        installation.saveInBackground();
    }
}
