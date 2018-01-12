package com.aspire.developerjohn.mycameraapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTakePic;
    private TextView tvHasCamera, tvHasCameraApp;
    private ImageView ivThumbnailPhoto;
    private Bitmap bitMap;
    private static int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get reference to views
        tvHasCamera = findViewById(R.id.tvHasCamera);
        tvHasCameraApp = findViewById(R.id.tvHasCameraApp);
        btnTakePic = findViewById(R.id.btnTakePic);
        ivThumbnailPhoto = findViewById(R.id.ivThumbnailPhoto);

        //Does your device has camera?
        if (hasCamera()){
            tvHasCamera.setBackgroundColor(0xFF00CC00);
            tvHasCamera.setText("You have Camera");
        }

        if (hasDefaultCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)){
            tvHasCameraApp.setBackgroundColor(0xFF00CC00);
            tvHasCameraApp.setText("You have a Camera App");
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA);
    }

    private boolean hasDefaultCameraApp(String action){
        final PackageManager packageManager = getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, packageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //To save picture
        File file = new File(Environment.getExternalStorageDirectory(),"my-photo.jpg");
        Uri photoPath = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoPath);

        //Start Camera activity
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && getIntent() != null){
            //get bundle
            Bundle extras = getIntent().getExtras();
            
            //get
            bitMap = (Bitmap) extras.get("data");
            ivThumbnailPhoto.setImageBitmap(bitMap);
        }
    }
}
