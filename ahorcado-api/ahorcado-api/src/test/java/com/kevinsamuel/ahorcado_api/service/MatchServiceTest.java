package com.kevinsamuel.ahorcado_api.service;

import com.kevinsamuel.ahorcado_api.dto.MatchRequestDTO;
import com.kevinsamuel.ahorcado_api.dto.MatchResultDTO;
import com.kevinsamuel.ahorcado_api.model.Match;
import com.kevinsamuel.ahorcado_api.model.Player;
import com.kevinsamuel.ahorcado_api.repository.MatchRepository;
import com.kevinsamuel.ahorcado_api.repository.PlayerRepository;
import com.kevinsamuel.ahorcado_api.strategy.ScoringStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private ScoringStrategy scoringStrategy;

    @InjectMocks
    private MatchService matchService;

    @Test
    void processFinishedMatch_NewPlayer_ShouldSaveAndReturnResult() {
        // Arrange
        MatchRequestDTO requestDTO = new MatchRequestDTO("newPlayer", "test", 3, 40);
        Player newPlayer = new Player("newPlayer");
        int calculatedScore = 150;
        int rankingPosition = 5;

        when(playerRepository.findByUsername("newPlayer")).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(newPlayer);
        when(scoringStrategy.calculateScore(anyInt(), anyInt(), anyInt())).thenReturn(calculatedScore);
        when(matchRepository.countPlayersWithHigherScore(calculatedScore)).thenReturn(rankingPosition - 1);

        // Act
        MatchResultDTO resultDTO = matchService.processFinishedMatch(requestDTO);

        // Assert
        assertEquals(calculatedScore, resultDTO.getScoreObtained());
        assertEquals(rankingPosition, resultDTO.getRankingPosition());

        verify(playerRepository, times(1)).findByUsername("newPlayer");
        verify(playerRepository, times(1)).save(any(Player.class));
        verify(matchRepository, times(1)).save(any(Match.class));
        verify(matchRepository, times(1)).countPlayersWithHigherScore(calculatedScore);
    }
}
