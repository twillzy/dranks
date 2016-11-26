import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';
window.navigator.userAgent = 'ReactNative';
const io = require('socket.io-client/socket.io');

class HelloWorld extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      budget : 200,
      spent: 0,
      isPoor: false,
      drinks: []
    };
    const socket = io('https://fathomless-peak-84606.herokuapp.com/', {
      transports: ['websocket']
    });
    socket.on('drinkBought', (data) => {
      this.setState({spent: data.totalPrice});
    });
    socket.on('')

  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.start}>Budget: ${this.state.budget}</Text>
        <Text style={styles.start}>Budget: ${this.state.spent}</Text>
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