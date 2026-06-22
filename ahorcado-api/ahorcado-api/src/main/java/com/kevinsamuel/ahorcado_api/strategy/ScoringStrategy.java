package com.kevinsamuel.ahorcado_api.strategy;

public interface ScoringStrategy {
    int calculateScore(String word, int mistakes, int timeElapsedSeconds);
}
