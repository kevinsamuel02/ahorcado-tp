package com.kevinsamuel.ahorcado_api.strategy;

public interface ScoringStrategy {
    int calculateScore(int wordLength, int mistakes, int timeElapsedSeconds);
}
