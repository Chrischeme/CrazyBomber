package com.mygdx.crazybomber.ui;
import java.util.ArrayList;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import java.util.Stack;

public class Repository {

    public Repository() {
        populateRepository();
    }

    private ArrayList<GameMap> _listOfMaps = new ArrayList<GameMap>();
    private ArrayList<Room> _listOfRooms = new ArrayList<Room>();
    private Stack<Screen> _listOfStates = new Stack<Screen>();

    public ArrayList<GameMap> getListOfMaps (){
        return _listOfMaps;
    }

    public ArrayList<Room> getListOfRooms (){
        return _listOfRooms;
    }

    public Stack<Screen> getListOfStates () {
        return _listOfStates;
    }

    private void populateRepository() {
        GameMap map = new GameMap(new Texture("backgroundimg3.png"), this);
        GameMap map1 = new GameMap(new Texture("backgroundimg2.png"), this);
        _listOfMaps.add(map);
        _listOfMaps.add(map1);
        Room room = new Room(new Texture("backgroundimg1.png"), this);
        Room room1 = new Room(new Texture("backgroundimg3.png"), this);
        Room room2 = new Room(new Texture("backgroundimg2.png"), this);
        _listOfRooms.add(room);
        _listOfRooms.add(room1);
        _listOfRooms.add(room2);
    }
}
