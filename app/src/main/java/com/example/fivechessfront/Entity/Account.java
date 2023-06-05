package com.example.fivechessfront.Entity;

//游戏注册者
public class Account {
    String name;
    String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return this.toString();
    }
}

