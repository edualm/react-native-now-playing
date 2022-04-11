package io.edr.nowplaying.reactnative;

import android.content.Intent;

public class NowPlayingInfo {

    long playbackTime;
    long playbackDuration;

    String title;
    String artist;
    String album;

    String artwork;

    NowPlayingInfo(Intent intent) {
        playbackTime = (long) 0;
        playbackDuration = intent.getLongExtra("duration", 0);

        title = intent.getStringExtra("track");
        artist = intent.getStringExtra("artist");
        album = intent.getStringExtra("album");

        artwork = null;
    }
}
