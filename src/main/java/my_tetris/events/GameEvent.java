package my_tetris.events;

import my_tetris.Element;
import my_tetris.general_game_objects.AbstractShape;
import my_tetris.general_game_objects.Glass;

import java.util.ArrayList;
import java.util.EventObject;

public class GameEvent extends EventObject {

    private Glass newGlass;
    
    private AbstractShape newShape;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameEvent(Object source) {
        super(source);
    }

    public Glass getNewGlass() {
        return newGlass;
    }

    public void setNewGlass(Glass newGlass) {
        this.newGlass = newGlass;
    }

    public AbstractShape getNewShape() {
        return newShape;
    }

    public void setNewShape(AbstractShape newShape) {
        this.newShape = newShape;
    }
}
