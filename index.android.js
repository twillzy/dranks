import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';
import NFCModule from './App/Modules/NFCModule';

class HelloWorld extends React.Component {
  constructor() {
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.start}>Tag NFC to Start Partying</Text>
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