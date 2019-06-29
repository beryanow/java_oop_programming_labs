package ru.nsu.g.beryanov.factory;

import ru.nsu.g.beryanov.gui.Graphics;

import java.util.Observable;
import java.util.Observer;

public class SleepChannel implements Observer {
    private static Graphics myGraphics;

    @Override
    public void update(Observable agency, Object newsItem) {
        myGraphics.getStatusWorkers()[(int) newsItem - 1].setText("Sleeping...");
    }
    public SleepChannel(Graphics y) {
        myGraphics = y;
    }
    SleepChannel() {}
}

