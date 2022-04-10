import { NativeEventEmitter, NativeEventSubscription, NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-now-playing' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

export interface NowPlayingInfo {

  playbackTime: number;
  playbackDuration: number;

  title: string | null;
  artist: string | null;
  album: string | null;

  artwork: string | null;
}

export interface NowPlayingState {

  isPlaying: boolean;
  item: NowPlayingInfo | null;
}

const NowPlayingNative = NativeModules.NowPlaying
  ? NativeModules.NowPlaying
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const NowPlayingEventEmitter = new NativeEventEmitter(NowPlayingNative);

class NowPlaying {

  callbacks = new Map<string, NativeEventSubscription>();

  startObserving(callback: (state: NowPlayingState) => any, identifier: string) {
    if (this.callbacks.size == 0)
      NowPlayingNative.startObserving();
  
    const listener = NowPlayingEventEmitter.addListener("NOW_PLAYING", (json) => {
      let nowPlaying = JSON.parse(json);

      callback(nowPlaying);
    });

    this.callbacks.set(identifier, listener);
  }

  stopObserving(identifier: string) {
    this.callbacks.get(identifier)?.remove();
    this.callbacks.delete(identifier);

    if (this.callbacks.size == 0)
      NowPlayingNative.stopObserving();
  }
}

export default (new NowPlaying());
