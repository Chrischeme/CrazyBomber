package com.mygdx.crazybomber.server;

import java.util.ArrayList;

public class ServerGameState {
    private ArrayList<ServerPlayer> _playerList;
    public ServerMap _map;

    public ArrayList<ServerPlayer> getPlayerList() {
        return _playerList;
    }

    public ServerMap getMap() {
        return _map;
    }

    public ServerGameState(int[][] intMap, ArrayList<ServerPlayer> playerArrayList){
        _playerList = playerArrayList;
        _map = new ServerMap(intMap);
    }

}