import React from 'react';
import {AppRegistry, StyleSheet, Text, View, TouchableHighlight, Image} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
const beer = {id: 1, name: 'beer', price: 8, calories: 100};
const martini = {id: 2, name: 'martini', price: 20, calories: 300};
const beerIcon = (<Icon name="beer" size={60} color="#FD5800"/>);
const cocktailIcon = (<Icon name="glass" size={60} color="#FD5800"/>);
const timesIcon = (<Icon name="times" size={20} color="grey"/>);
window.navigator.userAgent = 'ReactNative';
const io = require('socket.io-client/socket.io');

class HelloWorld extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      budget : 200,
      spent: 0,
      remaining: 200,
      isPoor: false,
      beer,
      martini,
      beerQuantity: 0,
      martiniQuantity: 0,
      imageUrl: './will.png',
      totalBeerCalories: 0,
      totalMartiniCalories: 0,
    };
    const socket = io('https://fathomless-peak-84606.herokuapp.com/', {transports: ['websocket']});
    socket.on('drinkBought', (data) => {
      this.setState({
        spent: data.totalPrice,
        remaining: this.state.remaining - data.totalPrice,
        beerQuantity: this.state.beerQuantity + data.quantities[0],
        martiniQuantity: this.state.martiniQuantity + data.quantities[1],
        totalBeerCalories: data.quantities[0] * beer.calories,
        totalMartiniCalories: data.quantities[1] * martini.calories,
      });
      if(this.state.budget < this.state.spent) {
        this.setState({
          isPoor: true,
          imageUrl: './poor.png'
        });
      }
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.header}>
          <Image style={styles.profilePic} source={require(this.state.imageUrl)}/>
          <Text style={styles.profileNameText}>twillzy</Text>
        </View>
        <View style={styles.subHeader}>
          <Text style={styles.budgetText}>Budget: $200</Text>
          <Text style={styles.budgetText}>Remaining: ${this.state.remaining}</Text>
        </View>
        <View style={styles.body}>
          {!!this.state.beerQuantity && <View style={styles.drinkPanel}>
            <View style={styles.drinkPanelLeft}>
              {beerIcon}
            </View>
            <View style={styles.drinkMiddlePanel}>
              <Text style={styles.quantity}>{this.state.totalBeerCalories}</Text>
              <Text style={styles.price}>Total Calories</Text>
            </View>
            <View style={styles.drinkPanelRight}>
              <Text style={styles.quantity}>{timesIcon} {this.state.beerQuantity}</Text>
              <Text style={styles.price}>Price: ${beer.price}</Text>
            </View>
          </View>}
          {!!this.state.martiniQuantity && <View style={styles.drinkPanel}>
            <View style={styles.drinkPanelLeft}>
              {cocktailIcon}
            </View>
            <View style={styles.drinkMiddlePanel}>
              <Text style={styles.quantity}>{this.state.totalMartiniCalories}</Text>
              <Text style={styles.price}>Total Calories</Text>
            </View>
            <View style={styles.drinkPanelRight}>
              <Text style={styles.quantity}>{timesIcon} {this.state.martiniQuantity}</Text>
              <Text style={styles.price}>Price: ${martini.price}</Text>
            </View>
          </View>}
        </View>
        <View style={styles.footer}>
          <Text style={styles.totalPrice}>Total Spent: ${this.state.spent}</Text>
        </View>
      </View>
    )
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F7F7F9',
  },
  header: {
    flex: 2,
    backgroundColor: '#DD1164',
    justifyContent: 'center',
    alignItems: 'center',
  },
  subHeader: {
    paddingLeft: 20,
    paddingTop: 10,
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  profilePic: {
    width: 80,
    height: 80,
    borderRadius: 40,
  },
  profileNameText: {
    fontFamily: 'sans-serif-condensed',
    fontWeight: 'bold',
    fontSize: 20,
    color: '#4D394B',
  },
  body: {
    justifyContent: 'center',
    flex: 5,
    paddingLeft: 10,
    paddingRight: 10,
  },
  drinkPanel: {
    marginTop: 10,
    marginBottom: 10,
    padding: 15,
    flexDirection: 'row',
    backgroundColor: 'white',
  },
  drinkPanelLeft: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  drinkMiddlePanel: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  drinkPanelRight: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  quantity: {
    color: 'grey',
    fontSize: 50,
  },
  price: {
    color: 'grey',
    fontSize: 15,
  },
  footer: {
    flex: 1,
    paddingLeft: 10,
    backgroundColor: '#009CDE',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-around',
  },
  totalPrice: {
    flex: 5,
    fontFamily: 'sans-serif-condensed',
    fontWeight: 'bold',
    fontSize: 50,
    color: 'white',
  },
});

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);