package edu.charlotte.assignment12.models;

public class Quality {
    String name;
    int level;

    public Quality(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Quality() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name;
    }
}
