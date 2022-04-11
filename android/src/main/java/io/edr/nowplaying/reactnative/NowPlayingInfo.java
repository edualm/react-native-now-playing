package io.edr.nowplaying.reactnative;

import android.content.Intent;

public class NowPlayingInfo {

    Double playbackTime;
    Double playbackDuration;

    String title;
    String artist;
    String album;

    String artwork;

    NowPlayingInfo(Intent intent) {
        playbackTime = (double) 0;
        playbackDuration = intent.getDoubleExtra("duration", 0);

        title = intent.getStringExtra("track");
        artist = intent.getStringExtra("artist");
        album = intent.getStringExtra("album");

        artwork = null;
    }
}
