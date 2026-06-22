package com.kevinsamuel.ahorcado_api.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        int wordLength = 5;
        int mistakes = 2;
        int timeElapsedSeconds = 30;
        // (5 * 100) - (30 * 2) - (2 * 50) = 500 - 60 - 100 = 340
        int expectedScore = 340;

        // Act
        int actualScore = scoringStrategy.calculateScore(wordLength, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(expectedScore, actualScore);
    }

    @Test
    void calculateScore_TooManyMistakes_ShouldReturnZero() {
        // Arrange
        int wordLength = 8;
        int mistakes = 6;
        int timeElapsedSeconds = 45;

        // Act
        int actualScore = scoringStrategy.calculateScore(wordLength, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(0, actualScore);
    }

    @Test
    void calculateScore_NegativeResult_ShouldReturnZero() {
        // Arrange
        int wordLength = 3;
        int mistakes = 5;
        int timeElapsedSeconds = 100;
        // (3 * 100) - (100 * 2) - (5 * 50) = 300 - 200 - 250 = -150 -> should be 0
        
        // Act
        int actualScore = scoringStrategy.calculateScore(wordLength, mistakes, timeElapsedSeconds);

        // Assert
        assertEquals(0, actualScore);
    }
}
