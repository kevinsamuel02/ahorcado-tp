package com.kevinsamuel.ahorcado_api.dto;

public class MatchRequestDTO {

    private String username;
    private String word;
    private int timeElapsedSeconds;
    private int mistakes;

    public MatchRequestDTO() {
    }

    public MatchRequestDTO(String username, String word, int timeElapsedSeconds, int mistakes) {
        this.username = username;
        this.word = word;
        this.timeElapsedSeconds = timeElapsedSeconds;
        this.mistakes = mistakes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getTimeElapsedSeconds() {
        return timeElapsedSeconds;
    }

    public void setTimeElapsedSeconds(int timeElapsedSeconds) {
        this.timeElapsedSeconds = timeElapsedSeconds;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }
}
