package com.nedaluof.qurany;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.nedaluof.qurany.databinding.ActivityDownloadBinding;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {
    ActivityDownloadBinding binding;
    private static final String TAG = "DownloadActivity";
    String SDPath = Environment.getExternalStorageDirectory().getPath() + "/Qurany/";
    private String url = "http://cdnserver8.mp3quran.net/ahmad_huth/113.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnDownload.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    //file_download(url);
                    Toast.makeText(this, getAppSpecificAlbumStorageDir(this, "Qurany").getPath(), Toast.LENGTH_LONG).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                } else {
                    // You can directly ask for the permission.
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0x1);
                }
            }
        });

    }


    public void file_download(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Qurany");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/Qurany/ahmad_huth", "113.mp3");

        mgr.enqueue(request);

    }


    @Nullable
    File getAppSpecificAlbumStorageDir(Context context, String fileName) {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);
        if (file == null || !file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    private void startDownload(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setDescription("Downloading mp3 file now ...");
        request.setTitle("Downloading");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //request.setDestinationInExternalPublicDir(path , "");
        request.setDestinationInExternalPublicDir( getAppSpecificAlbumStorageDir(this, "Qurany").getPath(), "/001.mp3");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            Log.d("DownloadActivity", "startDownload: ");
            manager.enqueue(request);
        }

    }


    private boolean checkFile(String path) {
        return new File(path).exists();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //prepareToDownload(url);
            } else {
                Toast.makeText(this, "permission write to external storage required", Toast.LENGTH_SHORT).show();
            }
        }
    }

}