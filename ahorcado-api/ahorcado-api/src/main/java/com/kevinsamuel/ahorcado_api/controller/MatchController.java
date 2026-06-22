package com.kevinsamuel.ahorcado_api.controller;

import com.kevinsamuel.ahorcado_api.dto.LeaderboardDTO;
import com.kevinsamuel.ahorcado_api.dto.MatchRequestDTO;
import com.kevinsamuel.ahorcado_api.dto.MatchResultDTO;
import com.kevinsamuel.ahorcado_api.service.MatchService;
import com.kevinsamuel.ahorcado_api.service.WordProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MatchController {

    private final MatchService matchService;
    private final WordProviderService wordProviderService;

    public MatchController(MatchService matchService, WordProviderService wordProviderService) {
        this.matchService = matchService;
        this.wordProviderService = wordProviderService;
    }

    @GetMapping("/word")
    public ResponseEntity<Map<String, String>> getWord() {
        String word = wordProviderService.getRandomWord();
        Map<String, String> response = Collections.singletonMap("word", word);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/matches")
    public ResponseEntity<MatchResultDTO> postMatch(@RequestBody MatchRequestDTO dto) {
        MatchResultDTO result = matchService.processFinishedMatch(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardDTO>> getLeaderboard() {
        List<LeaderboardDTO> leaderboard = matchService.getTop10Leaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
