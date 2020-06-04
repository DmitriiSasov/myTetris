/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris.events;


/**
 *
 * @author bopoh
 */
public interface GameListener {

    public void gameFinished();

    public void scoreChanged();
    
    public void glassWasSetup(GameEvent e);
    
    public void activeShapeChanged(GameEvent e);
}
