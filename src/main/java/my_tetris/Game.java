/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris;

import my_tetris.events.*;
import my_tetris.general_game_objects.Glass;
import my_tetris.general_game_objects.AbstractShape;

import java.util.ArrayList;

/**
 *
 * @author bopoh
 */
public class Game {
    
    private Glass glass;
    
    private ShapeFactory shapeFactory;
    
    private int score = 0;

    public boolean isInProgress() { return glass != null; }

    public Game() { shapeFactory = new ShapeFactory(); }

    public void start() {
        
        glass = new Glass(10, 20);
        glass.addGlassListener(new GlassObserver());
        
        GameEvent evt = new GameEvent(this);
        evt.setNewGlass(glass);
        fireGlassWasSetup(evt);

        exchangeActiveShape();        
    }
    
    private void exchangeActiveShape() {
        
        AbstractShape newShape = shapeFactory.createShape();

        GameEvent evt = new GameEvent(this);
        evt.setNewShape(newShape);
        fireActiveShapeChanged(evt);

        glass.setActiveShape(newShape);
    }
    
    private void changeScore(int delta) { 
    
        score += delta;        
        fireScoreChanged();
    }
    
    public void ShowScore() { System.out.print("\n***\nИгра закончилась\nВаш счет: " + score +  "\n***"); }

    public int getScore() { return score; }

    public ShapeFactory getShapeFactory() { return shapeFactory; }
    
    //Слушает ситуацию в стакане
    private class GlassObserver implements GlassListener {

        @Override
        public void glassFilled() {
            fireGameFinished();
        }

        @Override
        public void needNewActiveShape() { exchangeActiveShape(); }

        @Override
        public void shapeAbsorbed() { }

        @Override
        public void rowCleared(GlassEvent e) { changeScore(e.getRemovedElementsCount()); }

        @Override
        public void glassContentChanged() { }
    }

    //!!!Передает события UI
    private ArrayList<GameListener> listeners = new ArrayList<>();

    public void addGameListener(GameListener l) {

        if (!listeners.contains(l)) { listeners.add(l); }
    }

    public void removeGameListener(GameListener l) { listeners.remove(l); }

    public void fireGameFinished() {

        for (GameListener l: listeners) { l.gameFinished(); }
    }

    public void fireScoreChanged() {

        for (GameListener l: listeners) { l.scoreChanged(); }
    }

    private void fireGlassWasSetup(GameEvent e) {

        for (GameListener l: listeners) { l.glassWasSetup(e); }
    }

    private void fireActiveShapeChanged(GameEvent e) {

        for (GameListener l: listeners) { l.activeShapeChanged(e); }
    }
}
