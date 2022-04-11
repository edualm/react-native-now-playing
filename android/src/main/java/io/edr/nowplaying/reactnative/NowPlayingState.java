package io.edr.nowplaying.reactnative;

public class NowPlayingState {

    boolean isPlaying;
    NowPlayingInfo item;

    NowPlayingState(boolean isPlaying, NowPlayingInfo item) {
        this.isPlaying = isPlaying;
        this.item = item;
    }
}
