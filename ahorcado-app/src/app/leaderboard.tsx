import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import { router } from 'expo-router';
import { getLeaderboard } from '../services/api';

interface Score {
  username: string;
  score: number;
}

export default function Leaderboard() {
  const [scores, setScores] = useState<Score[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchLeaderboard = async () => {
      try {
        const data = await getLeaderboard();
        console.log("DATOS QUE MANDA JAVA:", data);
        setScores(data);
      } catch (error) {
        console.error('Failed to fetch leaderboard:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchLeaderboard();
  }, []);

  const renderScoreItem = ({ item, index }: { item: Score; index: number }) => (
      <View style={styles.scoreItem}>
        <Text style={[styles.itemText, styles.rank]}>{index + 1}</Text>
        <Text style={[styles.itemText, { width: '60%' }]}>{item.username}</Text>
        <Text style={[styles.itemText, styles.stats, { fontWeight: 'bold', color: '#5CBEBF' }]}>
          {item.score} pts
        </Text>
      </View>
  );

  if (loading) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color="#5CBEBF" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Tabla de Posiciones</Text>

      {scores.length === 0 ? (
        <View style={styles.centered}>
          <Text style={styles.noScoresText}>No hay puntuaciones registradas aún.</Text>
        </View>
      ) : (
        <>
          <View style={styles.listHeader}>
            <Text style={[styles.headerText, styles.rank]}>#</Text>
            <Text style={[styles.headerText, { width: '60%' }]}>Jugador</Text>
            <Text style={[styles.headerText, styles.stats]}>Puntos</Text>
          </View>
          <FlatList
            data={scores}
            renderItem={renderScoreItem}
            keyExtractor={(item, index) => index.toString()}
            style={styles.list}
          />
        </>
      )}

      <TouchableOpacity style={styles.button} onPress={() => router.replace('/')}>
        <Text style={styles.buttonText}>VOLVER A JUGAR</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
    alignItems: 'center',
    padding: 20,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: 'white',
    backgroundColor: '#5CBEBF',
    paddingVertical: 10,
    paddingHorizontal: 30,
    borderRadius: 10,
    marginTop: 40,
    marginBottom: 20,
    textAlign: 'center',
    overflow: 'hidden',
  },
  list: {
    width: '100%',
  },
  listHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 10,
    paddingBottom: 10,
    borderBottomWidth: 2,
    borderBottomColor: '#5CBEBF',
    width: '100%',
  },
  headerText: {
    color: '#333',
    fontWeight: 'bold',
    fontSize: 16,
  },
  scoreItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
    alignItems: 'center',
  },
  itemText: {
    fontSize: 16,
  },
  rank: {
    width: '10%',
    fontWeight: 'bold',
  },
  username: {
    width: '40%',
  },
  stats: {
    width: '25%',
    textAlign: 'center',
  },
  noScoresText: {
    fontSize: 18,
    color: '#666',
  },
  button: {
    backgroundColor: '#5CBEBF',
    paddingVertical: 15,
    paddingHorizontal: 40,
    borderRadius: 8,
    marginTop: 20,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
