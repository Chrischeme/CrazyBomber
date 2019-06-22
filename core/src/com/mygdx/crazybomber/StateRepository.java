package com.mygdx.bomberbois;
import com.badlogic.gdx.Screen;

import java.util.Stack;

public class StateRepository {
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

