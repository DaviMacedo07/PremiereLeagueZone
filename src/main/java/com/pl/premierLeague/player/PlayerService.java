package com.pl.premierLeague.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }
    public List<Player> getPlayersFromTea(String teamName) {
        return playerRepository.findAll().stream()
                .filter(p -> teamName.equals(p.getTeam()))
                .collect(Collectors.toList());
    }

    public  List<Player> getPlayersByName(String searchText) {
        return  playerRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPos(String searchText) {
        return playerRepository.findAll().stream()
                .filter(p -> p.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String serachText) {
        return  playerRepository.findAll().stream()
                .filter(p -> p.getNation().toLowerCase().contains(serachText)).collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPos(String team, String position) {
        return playerRepository.findAll().stream()
                .filter(p -> team.equals(p.getTeam()) && position.equals(p.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer( Player updatePlayer) {
        Optional<Player> existingPlayer = playerRepository.findByName(updatePlayer.getName());
        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatePlayer.getName());
            playerToUpdate.setTeam(updatePlayer.getTeam());
            playerToUpdate.setPos(updatePlayer.getPos());
            playerToUpdate.setNation(updatePlayer.getNation());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return  null;
    }

    @Transactional
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }
}
