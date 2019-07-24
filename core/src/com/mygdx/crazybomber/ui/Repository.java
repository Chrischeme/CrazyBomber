package com.mygdx.crazybomber.ui;
import java.util.ArrayList;
import com.badlogic.gdx.Screen;
import java.util.Stack;

public class Repository {

    public Repository() {
        populateRepository();
    }

    private ArrayList<GameMap> listOfMaps = new ArrayList<GameMap>();
    private ArrayList<Room> listOfRooms = new ArrayList<Room>();
    private Stack<Screen> listOfStates = new Stack<Screen>();

    public ArrayList<GameMap> getListOfMaps (){
        return listOfMaps;
    }

    public ArrayList<Room> getListOfRooms (){
        return listOfRooms;
    }

    public Stack<Screen> getListOfStates () {
        return listOfStates;
    }

    private void populateRepository() {
        GameMap map = new GameMap((new Texture("background.jpg")),repository);
        GameMap map1 = new GameMap((new Texture("background2.png")),repository);
        repository.addMap(map);
        repository.addMap(map1);
        Room room = new Room((new Texture("background.jpg")));
        Room room1 = new Room((new Texture("background1.png")));
        Room room2 = new Room((new Texture("background2.png")));
        repository.addRoom(room);
        repository.addRoom(room1);
        repository.addRoom(room2);
    }
}
