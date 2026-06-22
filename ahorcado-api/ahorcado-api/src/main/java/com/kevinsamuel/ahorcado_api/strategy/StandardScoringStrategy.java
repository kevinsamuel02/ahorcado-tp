package com.kevinsamuel.ahorcado_api.strategy;

import org.springframework.stereotype.Component;

@Component
public class StandardScoringStrategy implements ScoringStrategy {

    private static final int MAX_MISTAKES_ALLOWED = 6;
    private static final int SCORE_PER_LETTER = 100;
    private static final int PENALTY_PER_SECOND = 2;
    private static final int PENALTY_PER_MISTAKE = 50;

    @Override
    public int calculateScore(int wordLength, int mistakes, int timeElapsedSeconds) {
        if (mistakes >= MAX_MISTAKES_ALLOWED) {
            return 0;
        }

        int baseScore = wordLength * SCORE_PER_LETTER;
        int timePenalty = timeElapsedSeconds * PENALTY_PER_SECOND;
        int mistakesPenalty = mistakes * PENALTY_PER_MISTAKE;

        int finalScore = baseScore - timePenalty - mistakesPenalty;

        return Math.max(0, finalScore);
    }
}
