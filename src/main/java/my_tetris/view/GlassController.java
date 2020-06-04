package my_tetris.view;

import my_tetris.events.GlassEvent;
import my_tetris.events.GlassListener;
import my_tetris.general_game_objects.Glass;

public class GlassController {

    private Glass glass;

    private ElementsPanel elementsPanel;
    
    private GlassListener listener = new GlassObserver();
    
    public void setGlass(Glass glass) { 
    
        //if (this.glass != null) { this.glass.removeGlassListener(listener);}
        
        this.glass = glass;
        glass.addGlassListener(listener);
        if (elementsPanel.elementsMatrixesCount() == 0) { elementsPanel.addElementsMatrix(glass); }
        else { elementsPanel.replaceElementsMatrix(0, glass); }
        elementsPanel.update();
    }

    public ElementsPanel getElementsPanel() { return elementsPanel; }

    public void setElementsPanel(ElementsPanel elementsPanel) { this.elementsPanel = elementsPanel; }
    
    private class GlassObserver implements GlassListener {

        @Override
        public void glassFilled() { }

        @Override
        public void needNewActiveShape() { }

        @Override
        public void shapeAbsorbed() { elementsPanel.update(); }

        @Override
        public void rowCleared(GlassEvent e) { }

        @Override
        public void glassContentChanged() { elementsPanel.update(); }
    }
}
