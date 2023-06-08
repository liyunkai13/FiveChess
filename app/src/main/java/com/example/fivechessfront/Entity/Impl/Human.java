package com.example.fivechessfront.Entity.Impl;

import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.Enums.PlayerType;

public class Human extends Player {
    public Human(String name, Game game) {
        super(name, PlayerType.Human, game);
    }
}
