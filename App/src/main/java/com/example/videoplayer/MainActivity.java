package com.example.videoplayer;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    VideoView video;
    MediaController mediaController;
    EditText editText;
    ImageButton button_play;
    ListView listView;
    ListAdapter adapter;
    ArrayList<String> arr=new ArrayList<>();
//Link utilisé:
//https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/flood.mp4?alt=media&token=179d7e4e-7171-4a87-b1f8-b1fc3d976c60
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video = (VideoView) findViewById(R.id.video);
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        editText = (EditText) findViewById(R.id.url);
        button_play = (ImageButton) findViewById(R.id.btn_play);
        listView=(ListView)findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(adapter);


        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(v);
            }
        });
    }

    public void playVideo(View v) {
        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        String url = editText.getText().toString();
        Uri uri = Uri.parse(url);
        video.setVideoURI(uri);
        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pd.dismiss();
                mp.setLooping(true);
                video.start();

            }
        });
        arr.add(url);
        closeKeyboard();
        editText.setText("");
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
