package com.example.fivechessfront.utils;

import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;

public class Human extends Player {
    public Human(String name, Game game) {
        super(name,true, game);
    }
}
