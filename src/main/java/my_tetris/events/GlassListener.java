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
public interface GlassListener {
    
    public void glassFilled();

    public void needNewActiveShape();
    
    public void shapeAbsorbed();
    
    public void rowCleared(GlassEvent e);
    
    public void glassContentChanged();
}
