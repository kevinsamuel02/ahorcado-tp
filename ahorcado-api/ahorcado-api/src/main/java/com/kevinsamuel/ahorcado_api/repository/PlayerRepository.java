package com.kevinsamuel.ahorcado_api.repository;

import com.kevinsamuel.ahorcado_api.model.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);
}
