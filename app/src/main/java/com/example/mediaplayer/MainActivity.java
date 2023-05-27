package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    public TextView text;
    public String str = "ola";
    private SeekBar seekVolume;
    private AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        text = findViewById(R.id.textView);
        inicializarSeekBar();
        onStop();
    }

    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekBarVolume);

        //configura o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //recupera os valores de volume maximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekVolume.setMax(volumeMaximo);
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bool) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playSong(View view){
        //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        mediaPlayer.start();
        text.setText("Play");

    }
    public void pauseSong(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            text.setText("Pause");
        }

    }
    public void stopSong(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    protected void onStop(){
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

}