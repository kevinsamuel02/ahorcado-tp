package com.kevinsamuel.ahorcado_api.service.strategy;

public interface ScoringStrategy {
    int calculateScore(String word, int timeElapsedSeconds, int mistakes);
}
