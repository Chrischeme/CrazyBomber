package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.Player;
import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> _playerList;
    public Map _map;

    public ArrayList<Player> getPlayerList() {
        return _playerList;
    }

    public Map getMap() {
        return _map;
    }

    public GameState(int[][] intMap, ArrayList<Player> playerArrayList){
        _playerList = playerArrayList;
        _map = new Map(intMap);
    }

}