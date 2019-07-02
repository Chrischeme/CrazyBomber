package com.mygdx.crazybomber;
import java.util.ArrayList;

public class RoomRepository {
    ArrayList<Room> listOfRooms = new ArrayList<Room>();
    public void addRoom(Room room){
        listOfRooms.add(room);
    }
    public Room getRoom(int x){
        return listOfRooms.get(x);
    }
}