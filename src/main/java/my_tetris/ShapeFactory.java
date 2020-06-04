/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import my_tetris.events.*;
import my_tetris.general_game_objects.AbstractShape;
import my_tetris.general_game_objects.Shape;

/**
 *
 * @author bopoh
 */
public class ShapeFactory {

    /* Шаблон фигуры представлен в системе координат XY (первой указывается X)
     */
    private ArrayList<ShapeInfo> shapeTemplates = new ArrayList<>();

    private AbstractShape nextShape = null;
    
    public ShapeFactory() { createDefaultShapeTemplate(); }

    public AbstractShape getNextShape() { return nextShape.clone(); }

    private void createDefaultShapeTemplate() {

        int frequency = 10;
        AbstractShape shape = new Shape();
        ArrayList<ElementsMatrix> shapeForms = new ArrayList<>();
        
        
        ElementsMatrix shapeElements = new ElementsMatrix(2);
        //Создаем фигуру L
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeElements.add(new Element(new Point(3, 1)), 1);
        shapeForms.add(shapeElements);

        //Создаем фигуру J
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(1, 1)), 1);
        shapeForms.add(shapeElements);

        //Создаем фигуру I
        shapeElements = new ElementsMatrix(1);
        shapeElements.add(new Element(new Point(0, 0)), 0);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeForms.add(shapeElements);

        //Создаем фигуру S
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(3, 1)), 1);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeForms.add(shapeElements);

        //Создаем фигуру Z
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(1, 1)), 1);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeForms.add(shapeElements);

        //Создаем фигуру O
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(1, 1)), 1);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeForms.add(shapeElements);

        //Создаем фигуру T
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeForms.add(shapeElements);
        
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(254,232,0));
        colors.add(new Color(255, 255, 0));
        colors.add(new Color(255, 140, 0));
        colors.add(new Color(0, 139, 69));
        colors.add(new Color(0, 238, 0));

        ShapeInfo newTemplate = new ShapeInfo(frequency, shape, shapeForms, colors);
        
        shapeTemplates.add(newTemplate);
        
    }
    
    private void setColor(ElementsMatrix elements, int shapeTemplateIndex) {

        if (shapeTemplates.get(shapeTemplateIndex).shapeColorsCount() == 0) {
            
            throw new InvalidTemplateDataException("В шаблоне с индексом " + shapeTemplateIndex + "не указаны цвета, " +
                    "которыми можно покрасить фигуру");
        }
        
        int colorIndex = calcNextShapeColor(shapeTemplates.get(shapeTemplateIndex).shapeColorsCount());
        Color randomColor = shapeTemplates.get(shapeTemplateIndex).getShapeColor(colorIndex);

        Iterator<Element> iterator = elements.allElementsIterator();
        while (iterator.hasNext()) {
            iterator.next().setColor(randomColor);
        }
    }

    private int calcNextShapeColor(int ColorsCount) {

        return (int)Math.round(Math.random() * (ColorsCount - 1));
    }

    private int calcNextShapeType() {

        int frequency = (int)Math.round(Math.random() * 9 + 1);

        ArrayList<Integer> suitable = new ArrayList<>();

        int index = 0;
        for (ShapeInfo info : shapeTemplates) {
            
            if (info.getFrequency() >= frequency) { suitable.add(index); }
            ++index;
        }
        
        if (suitable.size() > 0) {
            
            return suitable.get((int)Math.round(Math.random() * (suitable.size() - 1)));
        } else {
            
            return (int)Math.round(Math.random() * (shapeTemplates.size() - 1));
        }
        
    }
    
    private int calcNextShapeForm(int FormsCount) {

        return (int)Math.round(Math.random() * (FormsCount - 1));
    }
    
    private AbstractShape chooseRandomShapeTemplate() {

        int shapeType = calcNextShapeType();
        ShapeInfo newShapeInfo = shapeTemplates.get(shapeType);
        int formType = calcNextShapeForm(newShapeInfo.shapeFormTemplatesCount());
        ElementsMatrix newShapeElements = newShapeInfo.getShapeFormTemplate(formType);
        setColor(newShapeElements, shapeType);
        
        Iterator<Element> iterator = newShapeElements.allElementsConstIterator();
        while (iterator.hasNext()) {
            
            Element tmp = iterator.next();
            if (tmp.getRow() < 0 && tmp.getCol() < 0) {
                
                throw new InvalidTemplateDataException("Форма фигуры с индексом " + formType + " задан неверно");
            }
        }
        AbstractShape result = newShapeInfo.getShapeTemplate().clone();
        result.setElements(newShapeElements);
        return result;
    }
    
    public AbstractShape createShape() {
        
        AbstractShape createdShape;
        if (nextShape != null) { createdShape = nextShape; } 
        else { createdShape = chooseRandomShapeTemplate(); }
        nextShape = chooseRandomShapeTemplate();        
        
        fireNextShapeChanged();

        return createdShape;
    }
    
    public void addShapeTemplate(ShapeInfo shapeInfo) { shapeTemplates.add(shapeInfo); }

    public void removeShapeTemplate(int templateIndex) { 
        
        if (templateIndex >= 0 && templateIndex < shapeTemplates.size()) { shapeTemplates.remove(templateIndex); }
    }
    
    public ShapeInfo getShapeTemplate(int templateIndex ) {

        if (templateIndex >= 0 && templateIndex < shapeTemplates.size()) { return shapeTemplates.get(templateIndex); }
        
        return null;
    }

    public int shapeTemplatesCount() { return shapeTemplates.size(); }
    
    //Сообщает игре о том, что следующая фигура поменялась
    private ArrayList<ShapeFactoryListener> listeners = new ArrayList<>();

    public void addShapeFactoryListener(ShapeFactoryListener l) {

        if (!listeners.contains(l)) { listeners.add(l); }
    }

    public void removeShapeFactoryListener(ShapeFactoryListener l) { listeners.remove(l); }

    private void fireNextShapeChanged() {

        for (ShapeFactoryListener l : listeners) { l.nextShapeChanged(); }
    }
    
    
    public class InvalidTemplateDataException extends IllegalStateException {

        public InvalidTemplateDataException(String s) { super(s); }
    } 
}



