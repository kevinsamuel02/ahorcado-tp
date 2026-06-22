package com.kevinsamuel.ahorcado_api.service;

import com.kevinsamuel.ahorcado_api.dto.LeaderboardDTO;
import com.kevinsamuel.ahorcado_api.dto.MatchRequestDTO;
import com.kevinsamuel.ahorcado_api.dto.MatchResultDTO;
import com.kevinsamuel.ahorcado_api.model.Match;
import com.kevinsamuel.ahorcado_api.model.Player;
import com.kevinsamuel.ahorcado_api.repository.MatchRepository;
import com.kevinsamuel.ahorcado_api.repository.PlayerRepository;
import com.kevinsamuel.ahorcado_api.strategy.ScoringStrategy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final ScoringStrategy scoringStrategy;

    public MatchService(PlayerRepository playerRepository, MatchRepository matchRepository, ScoringStrategy scoringStrategy) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.scoringStrategy = scoringStrategy;
    }

    @Transactional
    public MatchResultDTO processFinishedMatch(MatchRequestDTO dto) {
        Player player = playerRepository.findByUsername(dto.getUsername())
                .orElseGet(() -> playerRepository.save(new Player(dto.getUsername())));

        int score = scoringStrategy.calculateScore(
                dto.getWord().length(),
                dto.getMistakes(),
                dto.getTimeElapsedSeconds()
        );

        Match match = new Match(
                player,
                dto.getWord(),
                dto.getMistakes(),
                dto.getTimeElapsedSeconds(),
                score
        );

        matchRepository.save(match);

        int rankingPosition = matchRepository.countPlayersWithHigherScore(score) + 1;

        return new MatchResultDTO(score, rankingPosition);
    }

    public List<LeaderboardDTO> getTop10Leaderboard() {
        return matchRepository.findTopScores(PageRequest.of(0, 10));
    }
}
