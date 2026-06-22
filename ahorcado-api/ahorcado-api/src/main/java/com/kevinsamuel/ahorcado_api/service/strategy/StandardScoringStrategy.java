package com.kevinsamuel.ahorcado_api.service.strategy;

import org.springframework.stereotype.Service;

@Service("standardScoring")
public class StandardScoringStrategy implements ScoringStrategy {

    private static final int MAX_MISTAKES = 6;

    @Override
    public int calculateScore(String word, int timeElapsedSeconds, int mistakes) {
        if (mistakes >= MAX_MISTAKES) {
            return 0;
        }

        if (word == null || word.isEmpty()) {
            return 0;
        }

        int baseScore = word.length() * 100;
        int timePenalty = timeElapsedSeconds * 2;
        int mistakesPenalty = mistakes * 50;

        int calculatedScore = baseScore - timePenalty - mistakesPenalty;

        return Math.max(0, calculatedScore);
    }
}
