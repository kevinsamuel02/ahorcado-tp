package com.kevinsamuel.ahorcado_api.strategy;

public interface ScoringStrategy {
    /**
     * Calculates the score for a finished match.
     *
     * @param wordLength The length of the word that was played.
     * @param mistakes The number of mistakes made by the player.
     * @param timeElapsedSeconds The total time in seconds taken to finish the match.
     * @return The calculated score.
     */
    int calculateScore(int wordLength, int mistakes, int timeElapsedSeconds);
}
