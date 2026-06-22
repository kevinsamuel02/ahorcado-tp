import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native';
import { router } from 'expo-router';

export default function Home() {
  const [username, setUsername] = useState('');
/*
  const handlePlay = () => {
    // 1. La prueba del eco
    Alert.alert("¡Botón presionado!", `Intentando viajar con el usuario: ${username}`);

    if (!username.trim()) {
      Alert.alert('Nombre requerido', 'Por favor ingresa tu nombre');
      return;
    }

    // 2. El viaje
    router.push({ pathname: '/game', params: { username } });
  };
  */

  const handlePlay = () => {
    if (!username.trim()) {
      Alert.alert('Nombre de usuario requerido', 'Por favor, ingresa un nombre para continuar.');
      return;
    }
    router.push({ pathname: '/game', params: { username } });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Juego del Ahorcado</Text>
      <TextInput
        style={styles.input}
        placeholder="Ingresa tu nombre"
        value={username}
        onChangeText={setUsername}
      />
      <TouchableOpacity style={styles.button} onPress={handlePlay}>
        <Text style={styles.buttonText}>JUGAR</Text>
      </TouchableOpacity>

      <TouchableOpacity
          style={[styles.button, { backgroundColor: '#333', marginTop: 10 }]}
          onPress={() => router.push('/leaderboard')}
      >
        <Text style={styles.buttonText}>VER POSICIONES</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
    padding: 20,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    marginBottom: 40,
    color: '#333',
  },
  input: {
    width: '80%',
    height: 50,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    marginBottom: 20,
    fontSize: 16,
  },
  button: {
    backgroundColor: '#007bff',
    paddingVertical: 15,
    paddingHorizontal: 40,
    borderRadius: 8,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
