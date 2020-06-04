package my_tetris.view;

import my_tetris.Element;
import my_tetris.ElementsMatrix;
import my_tetris.ImmutableElementMatrix;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ElementsPanel extends JPanel {
    
    private int height;
    
    private int width;

    private final Color EMPTY_CELL_COLOR = new Color(245, 245, 245);

    private final Color CELL_BORDER_COLOR = new Color(131, 139, 131);

    private final int CELL_SIZE = 20;
    
    private ArrayList<ImmutableElementMatrix> matrixes = new ArrayList<>();

    public ElementsPanel(int height, int width) {
        
        setFocusable(true);
        
        this.height = height;
        this.width = width;

        setLayout(new GridLayout(height, width));
        for (int row = 1; row <= height; row++)
        {
            for (int col = 1; col <= width; col++)
            {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(CELL_SIZE,CELL_SIZE));
                cell.setBorder(BorderFactory.createLineBorder(CELL_BORDER_COLOR, 1));
                cell.setBackground(EMPTY_CELL_COLOR);
                add(cell);
            }

        }
        
    }
    
    private JPanel getCell(Point position) {
        
        int index = 0;
        Component widgets[] = getComponents();
        for(int i = widgets.length - 1; i >= 0; --i)
        {
            if(widgets[i] instanceof JPanel)
            {
                if(index == width * (position.y) + width - 1 - position.x)
                {
                    return (JPanel)widgets[i];
                }
                index++;
            }
        }

        return null;
    } 
    
    public void update() {
        
        for (Component widget : getComponents()) {

            if (widget instanceof JPanel && widget.getBackground() != EMPTY_CELL_COLOR) {
                widget.setBackground(EMPTY_CELL_COLOR);
            }
        }
        
        for (ImmutableElementMatrix matrix : matrixes) {

            Iterator<Element> iterator = matrix.allElementsConstIterator();
            while (iterator.hasNext()) {
                
                Element tmp = iterator.next();
                if (tmp.getCol() >= 0 && tmp.getCol() < width && tmp.getRow() >= 0 && tmp.getRow() < height) {
                    getCell(new Point(tmp.getCol(), tmp.getRow())).setBackground(tmp.getColor());
                }                
            }
        }
        
    }
    
    public void addElementsMatrix(ImmutableElementMatrix matrix) { matrixes.add(matrix); }
    
    public void addElementsMatrix(int insertIndex, ImmutableElementMatrix matrix) {
        
        if (insertIndex >= 0 && insertIndex < matrixes.size()) { matrixes.add(insertIndex, matrix); }
    } 
    
    public void removeElementsMatrix(int index) { 
        
        if (index >= 0 && index < matrixes.size()) { matrixes.remove(index); } 
    }
    
    public ImmutableElementMatrix getElementsMatrix(int index) {
        
        if (index >= 0 && index < matrixes.size()) { return matrixes.get(index); }
        else { return null; }
    }
    
    public int elementsMatrixesCount() { return matrixes.size(); }
    
    public void replaceElementsMatrix(int matrixToReplaceIndex, ImmutableElementMatrix newMatrix) {

        if (matrixToReplaceIndex >= 0 && matrixToReplaceIndex < matrixes.size()) { 
            matrixes.remove(matrixToReplaceIndex);
            matrixes.add(matrixToReplaceIndex, newMatrix);
        }
    }
}
