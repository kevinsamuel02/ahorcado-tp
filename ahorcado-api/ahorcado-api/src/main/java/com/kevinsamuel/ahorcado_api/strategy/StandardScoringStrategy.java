package com.kevinsamuel.ahorcado_api.strategy;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StandardScoringStrategy implements ScoringStrategy {

    private static final int MAX_MISTAKES_ALLOWED = 6;
    private static final List<Character> RARE_LETTERS = Arrays.asList('K', 'W', 'X', 'Y', 'Z');

    @Override
    public int calculateScore(String word, int mistakes, int timeElapsedSeconds) {
        if (mistakes >= MAX_MISTAKES_ALLOWED) {
            return 0;
        }

        if (word == null || word.isEmpty()) {
            return 0;
        }

        int baseScore = word.length() * 50;

        long rareLetterBonus = word.toUpperCase().chars()
                .mapToObj(c -> (char) c)
                .filter(RARE_LETTERS::contains)
                .count() * 20;

        int timePenalty = timeElapsedSeconds * 2;
        int mistakesPenalty = mistakes * 15;

        int finalScore = (int) (baseScore + rareLetterBonus - timePenalty - mistakesPenalty);

        return Math.max(0, finalScore);
    }
}
