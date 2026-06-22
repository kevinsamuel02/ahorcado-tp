package com.kevinsamuel.ahorcado_api.dto;

public class MatchResultDTO {

    private int scoreObtained;
    private int rankingPosition;

    public MatchResultDTO() {
    }

    public MatchResultDTO(int scoreObtained, int rankingPosition) {
        this.scoreObtained = scoreObtained;
        this.rankingPosition = rankingPosition;
    }

    public int getScoreObtained() {
        return scoreObtained;
    }

    public void setScoreObtained(int scoreObtained) {
        this.scoreObtained = scoreObtained;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

}
