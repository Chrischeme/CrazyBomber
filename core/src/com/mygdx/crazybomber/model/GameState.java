package com.mygdx.crazybomber.model;

import com.mygdx.crazybomber.model.player.Player;
import com.mygdx.crazybomber.model.map.Map;
import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> _playerList;
    private Map _map;

    public ArrayList<Player> getPlayerList() {
        return _playerList;
    }

    public Map getMap() {
        return _map;
    }

    public GameState(Map map){
        _playerList = new ArrayList<Player>();
        _map = map;
    }

}