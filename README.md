# React Native Now Playing

Get information about what's playing on the user's device.

**iOS support is done, Android is a work in progress.**

## Installation

```sh
npm install @edualm/react-native-now-playing
```

## Usage

```js
import NowPlaying from "react-native-now-playing";

// ...

NowPlaying.startObserving((state) => {
    console.log(state);
}, 'default');
```

## License

MIT
