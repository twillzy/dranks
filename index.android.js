import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import BLEControllerModule from './App/Modules/BLEControllerModule';

class HelloWorld extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      budget : 200
    };
    BLEControllerModule.cycleColours();
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.start}>Budget: ${this.state.budget}</Text>
      </View>
    )
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  start: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);