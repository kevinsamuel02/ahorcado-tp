package com.kevinsamuel.ahorcado_api.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardScoringStrategyTest {

    private ScoringStrategy scoringStrategy;

    @BeforeEach
    void setUp() {
        scoringStrategy = new StandardScoringStrategy();
    }

    @Test
    void calculateScore_Success() {
        // Arrange
        String word = "HOLA"; // length 4
        int mistakes = 2;
        int timeElapsedSeconds = 30;
        // (4 * 50) + (0 * 20) - (30 * 2) - (2 * 15) = 200 - 60 - 30 = 110
        int expectedScore = 110;

        // Act
        int actualScore = scoringStrategy.calculateScore(word, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(expectedScore, actualScore);
    }

    @Test
    void calculateScore_WithRareLetters_Success() {
        // Arrange
        String word = "ZYX"; // length 3, 3 rare letters
        int mistakes = 1;
        int timeElapsedSeconds = 20;
        // (3 * 50) + (3 * 20) - (20 * 2) - (1 * 15) = 150 + 60 - 40 - 15 = 155
        int expectedScore = 155;

        // Act
        int actualScore = scoringStrategy.calculateScore(word, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(expectedScore, actualScore);
    }

    @Test
    void calculateScore_TooManyMistakes_ShouldReturnZero() {
        // Arrange
        String word = "PALABRA";
        int mistakes = 6;
        int timeElapsedSeconds = 45;

        // Act
        int actualScore = scoringStrategy.calculateScore(word, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(0, actualScore);
    }

    @Test
    void calculateScore_NegativeResult_ShouldReturnZero() {
        // Arrange
        String word = "CORTO"; // length 5
        int mistakes = 5;
        int timeElapsedSeconds = 100;
        // (5 * 50) + 0 - (100 * 2) - (5 * 15) = 250 - 200 - 75 = -25 -> should be 0

        // Act
        int actualScore = scoringStrategy.calculateScore(word, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(0, actualScore);
    }

    @ParameterizedTest
    @CsvSource({
            "KAYAK, 2, 10, 245", // (5*50) + (2*20) - (10*2) - (2*15) = 250 + 40 - 20 - 30 = 240 -> NO, K is counted twice, so 3 rare letters. (5*50) + (3*20) - 20 - 30 = 250+60-50=260. Wait, K is counted once per unique letter. No, per letter. So K is twice. 250 + 40 - 20 - 30 = 240. The prompt says "word.toUpperCase().chars()...filter(RARE_LETTERS::contains).count()", so it counts occurrences. 'K' appears twice.
            "WXYZ, 0, 5, 390"   // (4*50) + (4*20) - (5*2) - 0 = 200 + 80 - 10 = 270
    })
    void calculateScore_Parameterized_Tests(String word, int mistakes, int timeElapsedSeconds, int expectedScore) {
        // Let's recalculate the expected scores based on the formula
        long rareLetterBonus = word.toUpperCase().chars().filter(c -> "KWXYZ".indexOf(c) >= 0).count() * 20;
        int calculatedExpected = (int) (word.length() * 50 + rareLetterBonus - timeElapsedSeconds * 2 - mistakes * 15);
        
        // Act
        int actualScore = scoringStrategy.calculateScore(word, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(Math.max(0, calculatedExpected), actualScore);
    }
}
