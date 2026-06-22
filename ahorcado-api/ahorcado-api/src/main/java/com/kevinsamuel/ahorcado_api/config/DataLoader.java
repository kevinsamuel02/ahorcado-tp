package com.kevinsamuel.ahorcado_api.config;

import com.kevinsamuel.ahorcado_api.model.Word;
import com.kevinsamuel.ahorcado_api.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;

    public DataLoader(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (wordRepository.count() == 0) {
            File file = ResourceUtils.getFile("classpath:palabras.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                wordRepository.save(new Word(line));
            }
        }
    }
}
