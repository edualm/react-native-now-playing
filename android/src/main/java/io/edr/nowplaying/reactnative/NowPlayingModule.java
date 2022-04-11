package io.edr.nowplaying.reactnative;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.*;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;

@ReactModule(name = NowPlayingModule.NAME)
public class NowPlayingModule extends ReactContextBaseJavaModule {

    static final class BroadcastTypes {
        static final String ANDROID_PLAYSTATE_CHANGED = "com.android.music.playstatechanged";
        static final String ANDROID_METADATA_CHANGED = "com.android.music.metadatachanged";
        static final String SPOTIFY_METADATA_CHANGED = "com.spotify.music.metadatachanged";
        static final String SPOTIFY_PLAYBACK_STATE_CHANGED = "com.spotify.music.playbackstatechanged";
    }

    public static final String NAME = "NowPlaying";

    private final ReactApplicationContext reactContext;

    private final BroadcastReceiver musicStateReceiver;

    private int listenerCount = 0;

    public NowPlayingModule(ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;

        musicStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            if (listenerCount == 0)
                return;

            boolean isPlaying = intent.getBooleanExtra("playing", false);

            NowPlayingState state;

            if (isPlaying)
                state = new NowPlayingState(true, new NowPlayingInfo(intent));
            else
                state = new NowPlayingState(false, null);

            Gson gson = new Gson();

            gson.toJson(state);

            sendEvent("NOW_PLAYING", (new Gson()).toJson(state));
            }
        };

        this.register(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BroadcastTypes.ANDROID_PLAYSTATE_CHANGED);
        intentFilter.addAction(BroadcastTypes.ANDROID_METADATA_CHANGED);

        intentFilter.addAction(BroadcastTypes.SPOTIFY_METADATA_CHANGED);
        intentFilter.addAction(BroadcastTypes.SPOTIFY_PLAYBACK_STATE_CHANGED);

        context.registerReceiver(musicStateReceiver, intentFilter);
    }

    @ReactMethod
    public void startObserving() {
        listenerCount++;
    }

    @ReactMethod
    public void stopObserving() {
        if (listenerCount > 0)
            listenerCount--;
    }

    private void sendEvent(String eventName,
                           String params) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }
}
