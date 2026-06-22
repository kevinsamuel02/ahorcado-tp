package com.kevinsamuel.ahorcado_api.repository;

import com.kevinsamuel.ahorcado_api.dto.LeaderboardDTO;
import com.kevinsamuel.ahorcado_api.model.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT new com.kevinsamuel.ahorcado_api.dto.LeaderboardDTO(m.player.username, MAX(m.score)) " +
           "FROM Match m GROUP BY m.player.username ORDER BY MAX(m.score) DESC")
    List<LeaderboardDTO> findTopScores(Pageable pageable);

    @Query("SELECT COUNT(DISTINCT m.player.username) FROM Match m WHERE m.score > :score")
    int countPlayersWithHigherScore(@Param("score") int score);
}
