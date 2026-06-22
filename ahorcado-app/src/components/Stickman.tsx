import React from 'react';
import { View, StyleSheet } from 'react-native';

interface StickmanProps {
  mistakes: number;
}

const STICK_COLOR = '#333';

const Stickman: React.FC<StickmanProps> = ({ mistakes }) => {
  return (
      <View style={styles.container}>
        {/* Estructura de la Horca */}
        <View style={styles.base} />
        <View style={styles.post} />
        <View style={styles.topBar} />
        <View style={styles.rope} />

        {/* Partes del Cuerpo */}
        {mistakes >= 1 && <View style={styles.head} />}
        {mistakes >= 2 && <View style={styles.body} />}
        {mistakes >= 3 && <View style={styles.leftArm} />}
        {mistakes >= 4 && <View style={styles.rightArm} />}
        {mistakes >= 5 && <View style={styles.leftLeg} />}
        {mistakes >= 6 && <View style={styles.rightLeg} />}
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 150,
    height: 200,
    position: 'relative',
    marginBottom: 20,
    alignSelf: 'center',
  },
  // --- HORCA ---
  base: {
    position: 'absolute',
    bottom: 0,
    left: 10,
    width: 100,
    height: 4,
    backgroundColor: STICK_COLOR,
  },
  post: {
    position: 'absolute',
    bottom: 0,
    left: 30,
    width: 4,
    height: 180,
    backgroundColor: STICK_COLOR,
  },
  topBar: {
    position: 'absolute',
    top: 20,
    left: 30,
    width: 80,
    height: 4,
    backgroundColor: STICK_COLOR,
  },
  rope: {
    position: 'absolute',
    top: 24,
    left: 106,
    width: 4,
    height: 30,
    backgroundColor: STICK_COLOR,
  },

  // --- MUÑECO ---
  // Eje central X = 108
  head: {
    position: 'absolute',
    top: 54,
    left: 96,
    width: 24,
    height: 24,
    borderRadius: 12,
    borderWidth: 3,
    borderColor: STICK_COLOR,
  },
  body: {
    position: 'absolute',
    top: 78,
    left: 106,
    width: 4,
    height: 50,
    backgroundColor: STICK_COLOR,
  },
  leftArm: {
    position: 'absolute',
    top: 90,
    left: 81,
    width: 30,
    height: 4,
    backgroundColor: STICK_COLOR,
    transform: [{ rotate: '-45deg' }],
  },
  rightArm: {
    position: 'absolute',
    top: 90,
    left: 105,
    width: 30,
    height: 4,
    backgroundColor: STICK_COLOR,
    transform: [{ rotate: '45deg' }],
  },
  leftLeg: {
    position: 'absolute',
    top: 133,
    left: 81,
    width: 30,
    height: 4,
    backgroundColor: STICK_COLOR,
    transform: [{ rotate: '-45deg' }],
  },
  rightLeg: {
    position: 'absolute',
    top: 133,
    left: 105,
    width: 30,
    height: 4,
    backgroundColor: STICK_COLOR,
    transform: [{ rotate: '45deg' }],
  },
});

export default Stickman;