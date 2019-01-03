package com.example.chinhtrinhquang.funquiz;

import android.app.ActionBar;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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

public class Settings extends AppCompatActivity {
    private SeekBar sbVolume;
    private SeekBar sbBrightness;
    private AudioManager audioManager;
    private int brightness;
    private ContentResolver contentResolver;
    private Window window;
    private Spinner langSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        VolumeControl();
        //BrightnessControl();
        //LangSpinnerControl();
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
                android.provider.Settings.System.putInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brightness / (float) 255;
                window.setAttributes(layoutParams);
            }
        });
    }
}