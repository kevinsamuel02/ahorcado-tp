package com.kevinsamuel.ahorcado_api.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ScoreEvaluatorService {

    private static final List<Character> RARE_LETTERS = Arrays.asList('K', 'W', 'X', 'Y', 'Z');


    public int calculateScore(String word, int timeElapsedSeconds, int mistakes) {
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
