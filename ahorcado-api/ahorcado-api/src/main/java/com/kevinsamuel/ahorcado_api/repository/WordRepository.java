package com.kevinsamuel.ahorcado_api.repository;

import com.kevinsamuel.ahorcado_api.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT text FROM words ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String getRandomWordText();
}
