import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import NowPlaying, { NowPlayingState } from '@edualm/react-native-now-playing';

export default function App() {
  const [state, setState] = React.useState<NowPlayingState | undefined>();

  const nowPlayingCallback = React.useCallback((state: NowPlayingState) => {
    console.log(state);
    
    setState(state);
  }, []);

  React.useEffect(() => {
    NowPlaying.startObserving(nowPlayingCallback, 'default');
  }, []);

  return (
    <View style={styles.container}>
      { state && state.isPlaying ? (
        <Text>{state.item?.title}</Text>
      ) : (
        <Text>Not playing.</Text>
      ) }
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
