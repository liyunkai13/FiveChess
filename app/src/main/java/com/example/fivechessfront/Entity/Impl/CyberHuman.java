package com.example.fivechessfront.Entity.Impl;

import com.example.fivechessfront.Entity.Game;
import com.example.fivechessfront.Entity.Player;
import com.example.fivechessfront.Enums.PlayerType;

public class CyberHuman extends Player {
    public CyberHuman(Game game) {
        super("", PlayerType.CyberHuman, game);
    }

}
