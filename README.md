# React Native Now Playing

Get information about what's playing on the user's device.

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
