package my_tetris;

import my_tetris.general_game_objects.AbstractShape;

import java.awt.*;
import java.util.ArrayList;

public class ShapeInfo {
    
    private int frequency;
    
    private AbstractShape shapeTemplate;
    
    private ArrayList<ElementsMatrix> shapeFormTemplates = new ArrayList<>();
    
    private ArrayList<Color> shapeColors = new ArrayList<>();

    public ShapeInfo(int frequency, AbstractShape shapeTemplate, ArrayList<ElementsMatrix> shapeFormTemplates, ArrayList<Color> shapeColors) {

        setFrequency(frequency);        
        this.shapeTemplate = shapeTemplate;
        this.shapeFormTemplates = shapeFormTemplates;
        this.shapeColors = shapeColors;
    }

    public ShapeInfo(int frequency, AbstractShape shapeTemplate) {
        
        this.frequency = frequency;
        this.shapeTemplate = shapeTemplate;
    }

    public int getFrequency() { return frequency; }

    public void setFrequency(int frequency) {
        
        if (frequency > 10) { this.frequency = 10; } 
        else if (frequency < 1) { this.frequency = 1; } 
        else { this.frequency = frequency; }    
    }

    public AbstractShape getShapeTemplate() { return shapeTemplate; }

    public void setShapeTemplate(AbstractShape shapeTemplate) { this.shapeTemplate = shapeTemplate; }
    
    public void addShapeForm(ElementsMatrix shapeForm) { shapeFormTemplates.add(shapeForm); }
    
    public void addColor(Color shapeColor) { shapeColors.add(shapeColor); }
    
    public void removeShapeForm(int shapeFormIndex) { 
        
        if (shapeFormIndex >= 0 && shapeFormIndex < shapeFormTemplates.size()) {
            
            shapeFormTemplates.remove(shapeFormIndex);
        }         
    }
    
    public void removeShapeColor(int colorIndex) {
        
        if (colorIndex >= 0 && colorIndex < shapeColors.size()) { shapeColors.remove(colorIndex); }
    }
    
    public int shapeColorsCount() { return shapeColors.size(); }
    
    public Color getShapeColor(int colorIndex) {

        if (colorIndex >= 0 && colorIndex < shapeColors.size()) { return shapeColors.get(colorIndex); }
        
        return null;
    }
    
    public int shapeFormTemplatesCount() { return shapeFormTemplates.size(); }
    
    public ElementsMatrix getShapeFormTemplate(int formTemplateIndex) {

        if (formTemplateIndex >= 0 && formTemplateIndex < shapeFormTemplates.size()) {
            
            return shapeFormTemplates.get(formTemplateIndex);
        }

        return null;
    }
}
