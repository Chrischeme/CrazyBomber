package com.mygdx.crazybomber;
import java.util.*;

//making a list of map objects
public class MapRepository {
    ArrayList<Map> listOfMaps = new ArrayList<Map>();
    public void addMap(Map map){
        listOfMaps.add(map);
    }
    public Map getMap(int x){
        return listOfMaps.get(x);
    }
    }
