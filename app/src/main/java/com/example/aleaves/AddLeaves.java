package com.example.aleaves;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.camera2.*;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;

public class AddLeaves extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leaves);

        //Check if the device has a camera available
        boolean hasCamera = checkCameraHardware(getApplicationContext());//Check if getApplicationContext() call correct
    }

    //TODO: Request access to camera and/or photos
    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    //TODO is this using up-to-date cam?
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }





}

