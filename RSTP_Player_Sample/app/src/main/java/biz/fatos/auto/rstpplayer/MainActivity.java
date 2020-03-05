package biz.fatos.auto.rstpplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String video_url="rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        CustomVideoView video = (CustomVideoView)findViewById(R.id.video);

        Uri uri = Uri.parse(video_url);
//        video.setMediaController(mc);
        video.setVideoURI(uri);
        video.start();

    }
}
