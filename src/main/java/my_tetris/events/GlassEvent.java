package my_tetris.events;

import java.util.EventObject;

public class GlassEvent extends EventObject {

    private int removedElementsCount = 0;
    
    public GlassEvent(Object source) {
        super(source);
    }

    public int getRemovedElementsCount() { return removedElementsCount; }

    public void setRemovedElementsCount(int removedElementsCount) { 
        
        if (removedElementsCount > 1) { this.removedElementsCount = removedElementsCount; } 
    }
}
