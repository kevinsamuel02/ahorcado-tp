const BASE_URL = 'http://10.0.2.2:8080/api';

export const getWord = async () => {
  try {
    const response = await fetch(`${BASE_URL}/word`);
    if (!response.ok) {
      throw new Error('Error encontrando la word');
    }
    return await response.json();
  } catch (error) {
    console.error('getWord error:', error);
    throw error;
  }
};

export const saveMatch = async (matchData) => {
  try {
    const response = await fetch('http://10.0.2.2:8080/api/matches', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(matchData),
    });

    // Leemos la respuesta como texto primero
    const responseText = await response.text();

    // Si hay texto, intentamos parsearlo a JSON. Si está vacío, devolvemos un objeto vacío.
    return responseText ? JSON.parse(responseText) : {};

  } catch (error) {
    console.error("Error al guardar la partida:", error);
  }
};


export const getLeaderboard = async () => {
  try {
    const response = await fetch(`${BASE_URL}/leaderboard`);
    if (!response.ok) {
      throw new Error('Error al encontrar el leaderboard');
    }
    return await response.json();
  } catch (error) {
    console.error('getLeaderboard error:', error);
    throw error;
  }
};
