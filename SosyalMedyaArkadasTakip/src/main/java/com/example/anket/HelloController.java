package com.example.anket;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class HelloController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene primaryStage;


    public HelloController(Scene primaryStage) {
        this.primaryStage = primaryStage;
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        primaryStage.setRoot( screenMap.get(name) );
    }
}
