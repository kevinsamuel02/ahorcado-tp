import React, { useState, useEffect, useMemo } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import { useLocalSearchParams, router } from 'expo-router';
import { getWord, saveMatch } from '../services/api';
import Stickman from '../components/Stickman';

const ALPHABET = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
const MAX_MISTAKES = 6;

export default function Game() {
  const { username } = useLocalSearchParams();
  const [word, setWord] = useState('');
  const [guessedLetters, setGuessedLetters] = useState<string[]>([]);
  const [mistakes, setMistakes] = useState(0);
  const [timeElapsedSeconds, setTimeElapsedSeconds] = useState(0);
  const [isGameOver, setIsGameOver] = useState(false);

  useEffect(() => {
    const fetchWord = async () => {
      try {
        const data = await getWord();
        setWord(data.word.toUpperCase());
      } catch (error) {
        Alert.alert('Error', 'No se pudo cargar una nueva palabra. Intenta de nuevo.', [
          { text: 'OK', onPress: () => router.replace('/') },
        ]);
      }
    };
    fetchWord();
  }, []);

  useEffect(() => {
    if (isGameOver) return;
    const timer = setInterval(() => {
      setTimeElapsedSeconds((prev) => prev + 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [isGameOver]);

  const hiddenWord = useMemo(() => {
    if (!word) return '';
    return word.split('').map((letter) => (guessedLetters.includes(letter) ? letter : '_')).join(' ');
  }, [word, guessedLetters]);

  const handleGuess = (letter: string) => {
    if (guessedLetters.includes(letter) || isGameOver) return;

    setGuessedLetters([...guessedLetters, letter]);

    if (!word.includes(letter)) {
      setMistakes((prev) => prev + 1);
    }
  };

  useEffect(() => {
    if (!word || isGameOver) return;

    const didWin = word.split('').every((letter) => guessedLetters.includes(letter));
    const didLose = mistakes >= MAX_MISTAKES;

    if (didWin || didLose) {
      setIsGameOver(true);

      const handleEndGame = async () => {
        const finalMistakes = didLose ? MAX_MISTAKES : mistakes;
        try {
          const result = await saveMatch({
            username: username as string,
            word,
            timeElapsedSeconds,
            mistakes: finalMistakes,
          });
          console.log("DEBUG: Datos recibidos del backend al guardar partida:", result);

          const { scoreObtained, rankingPosition } = result;
          
          Alert.alert(
            didWin ? '¡Ganaste!' : '¡Perdiste!',
            `La palabra era: ${word}\n\nPuntaje obtenido: ${scoreObtained}\nPosición en el ranking: #${rankingPosition}`,
            [{ text: 'Ver Leaderboard', onPress: () => router.replace('/leaderboard') }]
          );
        } catch (error) {
          console.error('Failed to save match:', error);
          Alert.alert(
            didWin ? '¡Ganaste!' : '¡Perdiste!',
            `La palabra era: ${word}\n\nNo se pudo guardar la puntuación.`,
            [{ text: 'Jugar de nuevo', onPress: () => router.replace('/') }]
          );
        }
      };

      handleEndGame();
    }
  }, [guessedLetters, mistakes, word, isGameOver, timeElapsedSeconds, username]);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>Jugador: {username}</Text>
        <Text style={styles.headerText}>Tiempo: {timeElapsedSeconds}s</Text>
        <Text style={styles.headerText}>Errores: {mistakes}/{MAX_MISTAKES}</Text>
      </View>

      <Stickman mistakes={mistakes} />

      <Text style={styles.word}>{hiddenWord}</Text>

      <View style={styles.keyboard}>
        {ALPHABET.map((letter) => {
          const isGuessed = guessedLetters.includes(letter);
          return (
            <TouchableOpacity
              key={letter}
              style={[styles.key, isGuessed && styles.keyGuessed]}
              onPress={() => handleGuess(letter)}
              disabled={isGuessed || isGameOver}
            >
              <Text style={styles.keyText}>{letter}</Text>
            </TouchableOpacity>
          );
        })}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-around',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
    padding: 10,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '100%',
    paddingHorizontal: 20,
    marginTop: 40,
  },
  headerText: {
    fontSize: 16,
    color: '#333',
  },
  word: {
    fontSize: 40,
    fontWeight: 'bold',
    letterSpacing: 8,
    marginVertical: 20,
  },
  keyboard: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    width: '100%',
    paddingHorizontal: 10,
  },
  key: {
    backgroundColor: '#5CBEBF',
    padding: 10,
    margin: 4,
    borderRadius: 5,
    width: 40,
    height: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
  keyGuessed: {
    backgroundColor: '#9e9e9e',
  },
  keyText: {
    color: 'white',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
