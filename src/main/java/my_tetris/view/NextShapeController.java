package my_tetris.view;

import my_tetris.ShapeFactory;
import my_tetris.events.ShapeFactoryListener;
import my_tetris.general_game_objects.AbstractShape;

public class NextShapeController {
    
    private ShapeFactory shapeFactory;

    private ElementsPanel elementsPanel;
    
    public ShapeFactory getShapeFactory() { return shapeFactory; }

    public void setShapeFactory(ShapeFactory shapeFactory) { 
        
        this.shapeFactory = shapeFactory; 
        shapeFactory.addShapeFactoryListener(new ShapeFactoryObserver());
    }

    public ElementsPanel getElementsPanel() { return elementsPanel; }

    public void setElementsPanel(ElementsPanel elementsPanel) { this.elementsPanel = elementsPanel; }
    
    private class ShapeFactoryObserver implements ShapeFactoryListener {

        @Override
        public void nextShapeChanged() {
            
            int count = elementsPanel.elementsMatrixesCount();
            if (count == 0) { elementsPanel.addElementsMatrix(shapeFactory.getNextShape()); }
            else { elementsPanel.replaceElementsMatrix(0, shapeFactory.getNextShape()); }
            elementsPanel.update();
        }
    }
}
