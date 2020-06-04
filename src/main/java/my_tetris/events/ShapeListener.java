package my_tetris.events;

public interface ShapeListener {
    
    public void shapeReadyToMove();
    
    public void shapeLocationChanged();
    
    public void shapeStopped();
}
