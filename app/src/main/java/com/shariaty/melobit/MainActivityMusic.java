package com.shariaty.melobit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivityMusic extends AppCompatActivity {

    private final List<MusicList> musicLists = new ArrayList<>();
    private RecyclerView musicRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decodeView = getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decodeView.setSystemUiVisibility(options);



        setContentView(R.layout.activity_music);

        final LinearLayout searchBtn = findViewById(R.id.searchBtn);
        final LinearLayout menuBtn = findViewById(R.id.menuBtn);
        musicRecyclerView = findViewById(R.id.musicREcyclerView);
        final CardView playPauseCard = findViewById(R.id.playpauseCard);
        final ImageView playPauseImg = findViewById(R.id.playpauseBtn);
        final ImageView nextBtn = findViewById(R.id.nextBtn);
        final ImageView prevBtn = findViewById(R.id.previousBtn);

        musicRecyclerView.setHasFixedSize(true);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            getMusicFiles();
        }
        else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},11);
            }
            else{
                getMusicFiles();

            }
        }

    }


       private void getMusicFiles(){
           ContentResolver contentResolver= getContentResolver();
           Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
           Cursor cursor= contentResolver.query(uri, null, MediaStore.Audio.Media.DATA+"LIKE?",new String[]{"%.mp3%"},null);
           if (cursor == null){
               Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();

           }else if(!cursor.moveToNext()) {
               Toast.makeText(this, "No music found", Toast.LENGTH_SHORT).show();



           }else{
               while (cursor.moveToNext()) {

                   final String getMusicFileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                   final String getArtistName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                   long cursorId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                   Uri musicFileUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorId);
                   String getDuration = "00:00";
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                       getDuration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                   }
                   final MusicList musicList = new MusicList(getMusicFileName,getArtistName,getDuration,false,musicFileUri);
                   musicLists.add(musicList);

               }
               musicRecyclerView.setAdapter(new MusicAdapter(musicLists,MainActivityMusic.this));
           }


        }

        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getMusicFiles();


        }
        else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }

        }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            View decodeView = getWindow().getDecorView();
            int options = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decodeView.setSystemUiVisibility(options);


        }
    }
}
