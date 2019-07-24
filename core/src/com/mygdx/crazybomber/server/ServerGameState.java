package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.map.Map;
import java.util.ArrayList;

public class ServerGameState {
    private ArrayList<Player> _playerList;
    public ServerMap _map;

    public ArrayList<Player> getPlayerList() {
        return _playerList;
    }

    public ServerMap getMap() {
        return _map;
    }

    public GameState(int[][] intMap, ArrayList<Player> playerArrayList){
        _playerList = playerArrayList;
        _map = new Map(intMap);
    }

}