package com.example.scratchapplication.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServiceCloudMessage extends Service {
    public MyServiceCloudMessage() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}