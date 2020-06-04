package my_tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ElementsMatrix implements Cloneable{
    
    private ArrayList<ArrayList<Element>> elements;

    public ElementsMatrix(int rowCount) {
        
        if (rowCount < 1) {
            
            throw new ElementsMatrixSizeException("Количество строк в матрице должно быть больше 0 ");
        }
        
        elements = new ArrayList<>();
        
        for (int i = 0; i < rowCount; i++) { elements.add(new ArrayList<>()); }        
    }
    
    public int rowCount() {
        
        return elements.size();
    }
    
    public Element remove(int index, int rowIndex) {
        
        if (rowIndex >= rowCount() || rowIndex < 0 || index >= elements.get(rowIndex).size() || index < 0) {
            
            return null;
        }
        
        return elements.get(rowIndex).remove(index);
    }
    
    public void remove(Element e) {

        for (ArrayList<Element> row : elements) { row.remove(e); }        
    }
    
    public void remove(ArrayList<Element> elements) {

        for (ArrayList<Element> row : this.elements) { row.removeAll(elements); }        
    }
    
    public boolean add(Element e, int rowIndex) {
        
        if (rowIndex < 0 || rowIndex >= elements.size() || contains(e)) { return false; }
    
        elements.get(rowIndex).add(e);
        return true;
    }
    
    public boolean add(ElementsMatrix em) {
        
        if (em.rowCount() <= rowCount()) {
            
            for (int i = 0; i < em.rowCount(); ++i) {

                for (Element e : em.elements.get(i)) {
                    
                    if (!contains(e)) { elements.get(i).add(e); }
                }                
            }            
        } else {
            
            return false;
        }
        
        return true;
    }    
    
    public boolean add(ArrayList<Element> elements, int rowIndex) {

        if (rowIndex < 0 || rowIndex >= this.elements.size()) { return false; }

        for (Element e : elements) {
            
            if (!contains(e)) { this.elements.get(rowIndex).add(e); }
        }
        
        return true;
    }
    
    public void move(Direction d) {

        for (ArrayList<Element> row: elements) {
            
            for (Element rowElement : row) { rowElement.move(d); }
        }
    }
    
    public void rotate() {

        int middleElementIndex = 0;
        int elementsCount = elementsCount();
        Element rotationCenter = null;
        
        middleElementIndex = (elementsCount % 2 == 0) ? elementsCount / 2 - 1 : elementsCount / 2;
        
        //Найти средний элемент
        int currentElementIndex = 0;
        for (int i = 0; i < elements.size() && rotationCenter == null; ++i) {

            for (int j = 0; j < elements.get(i).size() && rotationCenter == null; j++) {
                
                if (currentElementIndex == middleElementIndex) { rotationCenter = elements.get(i).get(j); }
                currentElementIndex++;
            } 
        }
        
        //Повернуть все элементы вокруг среднего элемента
        for (ArrayList<Element> row : elements) {
            
            for (Element rowElement : row) { rowElement.rotate(rotationCenter); }
        }        
    }
    
    public void clear() { elements.clear(); }
    
    public ArrayList<Element> clear(int rowIndex) {
        
        if (rowIndex >= 0 && rowIndex < elements.size()) { 
        
            ArrayList<Element> removedElements = (ArrayList<Element>) elements.get(rowIndex).clone(); 
            elements.get(rowIndex).clear();
            return removedElements;
        }
        
        return null;
    }
    
    public Element getElement(int index, int rowIndex) {
        
        if (rowIndex >= rowCount() || rowIndex < 0 || index >= elements.get(rowIndex).size() || index < 0) {
            return null;
        }
        return elements.get(rowIndex).get(index);
    }
    
    public boolean contains(Element e) {
        
        boolean res = false;
        for (int i = 0; i < elements.size() && res == false; i++) { res = elements.get(i).contains(e); }
        
        return res; 
    }
    
    public int elementsCount() {

        int elementsCount = 0;
        for (ArrayList<Element> row : elements) { elementsCount += row.size(); }
        
        return elementsCount;
    }
    
    public int elementsCount(int rowIndex) {
        
        if (rowIndex >= rowCount() || rowIndex < 0) { return 0; }
        
        return elements.get(rowIndex).size();
    }
    
    public Iterator<Element> iteratorByRow(int rowIndex) {

        if (rowIndex >= rowCount() || rowIndex < 0) { return null; }
        
        return elements.get(rowIndex).iterator();
    }
    
    public Iterator<Element> allElementsIterator() {

        ArrayList<Element> elementsList = new ArrayList<>();
        for (ArrayList<Element> row : elements) { elementsList.addAll(row); }
        
        return elementsList.iterator(); 
    }

    public Iterator<Element> constIteratorByRow(int rowIndex) {

        if (rowIndex >= rowCount() || rowIndex < 0) { return null; }

        ArrayList<Element> copy = new ArrayList<>();
        for (Element e : elements.get(rowIndex)) {
            
            try { copy.add(e.clone()); } 
            catch (CloneNotSupportedException ex) { ex.printStackTrace(); }
        }
        
        return copy.iterator();
    }

    public Iterator<Element> allElementsConstIterator() {

        ArrayList<Element> elementsList = new ArrayList<>();
        for (ArrayList<Element> row : elements) {
            
            for (Element rowElement : row) {

                try { elementsList.add(rowElement.clone()); } 
                catch (CloneNotSupportedException e) { e.printStackTrace(); }
            }            
        }

        return elementsList.iterator();
    }
    
    public boolean hasCommonElements(ElementsMatrix em) {

        boolean res = false;
        for (int i = 0; i < em.elements.size() && res == false; i++) {

            for (int j = 0; j < em.elements.get(i).size() && res == false; j++) {

                res = contains(em.elements.get(i).get(j));
            }
        }
        
        return res;
    }
    
    public void setStartPosition(int maxRowIndex, int minColIndex, int maxColIndex) {

        int colShift = (maxColIndex - minColIndex % 2 == 0) ? (maxColIndex - minColIndex) / 2 - 1 : 
                (maxColIndex - minColIndex) / 2;
        
        for (ArrayList<Element> row : elements) {

            for (Element rowElement : row ) {

                rowElement.setPosition(rowElement.getCol() + colShift,
                        rowElement.getRow() + maxRowIndex);
            }
        }
    }

    public ElementsMatrix clone() throws CloneNotSupportedException {
        
        ElementsMatrix em = new ElementsMatrix(rowCount());

        int rowIndex = 0;
        for (ArrayList<Element> row : elements) {

            for (Element rowElement : row) { em.elements.get(rowIndex).add(rowElement.clone()); }
            ++rowIndex;
        }
        
        return em;
    }

    public class ElementsMatrixSizeException extends IllegalArgumentException {
        
        public ElementsMatrixSizeException(String s) { super(s); }
    } 
}
