package com.example.chinhtrinhquang.funquiz;

import android.app.ActionBar;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private SeekBar sbVolume;
    private SeekBar sbBrightness;
    private AudioManager audioManager;
    private int brightness;
    private ContentResolver contentResolver;
    private Window window;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        VolumeControl();
        BrightnessControl();

    }

    private void VolumeControl() {
        try {
            sbVolume = (SeekBar) findViewById(R.id.sbVolume);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (Exception e) {
            return;
        }
    }

    private void BrightnessControl() {
        sbBrightness = (SeekBar) findViewById(R.id.sbBrightness);
        contentResolver = getContentResolver();
        window = getWindow();
        sbBrightness.setMax(255);
        sbBrightness.setKeyProgressIncrement(1);
        try {
            android.provider.Settings.System.putInt(getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            brightness = android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            Log.e("Lỗi", "Không thể thay đổi độ sáng");
            e.printStackTrace();
        }
        sbBrightness.setProgress(brightness);

        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 20) {
                    brightness = 20;
                } else {
                    brightness = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                boolean value = true;
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    value = android.provider.Settings.System.canWrite(getApplicationContext());
//                    if(value == false){
//                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                        intent.setData((Uri.parse("package: " + getApplicationContext().getPackageName())));
//                        startActivityForResult(intent, 1000);
//                    } else {
//                        success = true;
//                    }
//                }
//                if (success == true) {
//                    android.provider.Settings.System.putInt(Settings.this.contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
//                    WindowManager.LayoutParams layoutParams = window.getAttributes();
//                    layoutParams.screenBrightness = brightness / (float) 255;
//                    window.setAttributes(layoutParams);
//                }
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == 1000) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                boolean value = android.provider.Settings.System.canWrite(getApplicationContext());
//                if(value) {
//                    success = true;
//                } else {
//                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
}