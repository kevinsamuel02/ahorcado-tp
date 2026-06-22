package com.kevinsamuel.ahorcado_api.service;

import com.kevinsamuel.ahorcado_api.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
public class WordProviderService {

    private final WordRepository wordRepository;

    public WordProviderService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public String getRandomWord() {
        return wordRepository.getRandomWordText().toUpperCase();
    }
}
