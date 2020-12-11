package com.nedaluof.qurany.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nedaluof.qurany.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

/**
 * Created by nedaluof on 6/25/2020.
 */
public class Utility {

    public static final String BUNDLE_KEY = "BUNDLE_KEY";
    public static final String SURA_KEY = "SURA_KEY";
    public static final int PLAYBACK_NOTIFICATION_ID = 50;

    /**
     * @return user device language
     */
    @NotNull
    public static String getLanguage() {
        return Locale.getDefault().getDisplayLanguage().equals("العربية") ? "_arabic" : "_english";
    }

    /**
     * Create the main folder which will hold
     * downloaded Sura if not exist
     */
    public static void createMainFolder() {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Qurany");
        if (!direct.exists()) {
            direct.mkdirs();
        }
    }

    /**
     * @param subPath contain Reciter Name and Sura Name
     *                {Ahmad/AlFatiha.mp3}
     * @return true if exist , else not exist
     */
    public static boolean checkIfFileInPathExist(String subPath) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Qurany/" + subPath);
        return direct.exists();
    }



    public static void showSettingsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialoge_permission_title));
        builder.setMessage(context.getResources().getString(R.string.dialoge_permission_message));
        builder.setPositiveButton(context.getResources().getString(R.string.dialoge_permission_gosettings), (dialog, which) -> {
            dialog.cancel();
            openSettings(context);
        });
        builder.setNegativeButton(context.getResources().getString(R.string.dialoge_permission_cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * @param context identify package to navigate the user to app settings
     */
    private static void openSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }


    public static Animation getButtonAnimation(Context context) {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        myAnim.setInterpolator(new ButtonAnimation(0.2, 20));
        return myAnim;
    }

    public static void handleButtonAnimation(Context context, ImageButton button) {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        ButtonAnimation interpolator = new ButtonAnimation(0.2, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
    }
}
