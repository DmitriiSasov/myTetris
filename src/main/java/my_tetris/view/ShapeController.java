package my_tetris.view;

import my_tetris.Direction;
import my_tetris.events.ShapeListener;
import my_tetris.general_game_objects.AbstractShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeController {
    
    private AbstractShape shape;

    private javax.swing.Timer timer = new Timer(1000, new MovingShapeTask());

    private ElementsPanel elementsPanel;
    
    private ShapeListener listener = new ShapeObserver();
    
    public ShapeController() { timer.setRepeats(true); }

    public void setShape(AbstractShape shape) { 
        
        if (this.shape != null) { this.shape.removeShapeListener(listener); }
        
        this.shape = shape;
        shape.addShapeListener(listener);
        int count = elementsPanel.elementsMatrixesCount();
        if (count == 0 || count == 1) { elementsPanel.addElementsMatrix(shape); }
        else { elementsPanel.replaceElementsMatrix(1, shape); }
        elementsPanel.update();
    }

    public AbstractShape getShape() { return shape; }

    public ElementsPanel getElementsPanel() { return elementsPanel; }

    public void setElementsPanel(ElementsPanel elementsPanel) { this.elementsPanel = elementsPanel; }

    private void startTimer() { timer.start(); }

    private void stopTimer() { timer.stop(); }
    
    //Слушает события от таймера и двигает фигуру вниз
    private class MovingShapeTask implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) { shape.move(Direction.SOUTH); }
    }    
    
    private class ShapeObserver implements ShapeListener {

        @Override
        public void shapeReadyToMove() { startTimer(); }

        @Override
        public void shapeLocationChanged() { elementsPanel.update(); }

        @Override
        public void shapeStopped() { stopTimer(); }
    }
}
