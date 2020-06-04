package my_tetris;

import java.util.Iterator;

public interface ImmutableElementMatrix {
    
    public Iterator<Element> allElementsConstIterator();
    
    public Iterator<Element> constIteratorByRow(int index);
    
    public Element getElementCopy(int index, int rowIndex);
    
    public int elementsCount();
    
    public int elementsCount(int rowIndex);
    
    public boolean contains(Element e);
    
    public boolean hasCommonElements(ElementsMatrix em);
}
