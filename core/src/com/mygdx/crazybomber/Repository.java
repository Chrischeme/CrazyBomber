package com.mygdx.crazybomber;
import java.util.ArrayList;
import com.badlogic.gdx.Screen;
import java.util.Stack;

public class Repository {
    ArrayList<Map> listOfMaps = new ArrayList<Map>();
    public void addMap(Map map){
        listOfMaps.add(map);
    }
    public Map getMap(int x){
        return listOfMaps.get(x);
    }

    ArrayList<Room> listOfRooms = new ArrayList<Room>();
    public void addRoom(Room room){
        listOfRooms.add(room);
    }
    public Room getRoom(int x){
        return listOfRooms.get(x);
    }

    Stack<Screen> listOfStates = new Stack<Screen>();
    public void push(Screen screen){
        listOfStates.push(screen);
    }
    public Screen pop(){
        return listOfStates.pop();
    }
    public Screen peek(){
        return listOfStates.peek();
    }
}
