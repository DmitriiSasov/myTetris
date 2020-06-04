/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris;

import java.awt.*;

/**
 *
 * @author bopoh
 */
public class Element implements Cloneable{
    
    private Point position;

    private Color color;

    public Element(Point position, Color color) {
    
        this.position = position;
        this.color = color;
    }

    public Element(Point position) { this.position = position; }

    public Element(int col, int row) { this.position = new Point(col, row); }

    public Element(int col, int row, Color color) {
    
        this.position = new Point(col, row);
        this.color = color;
    }
    
    public int getCol() { return (int) position.getX(); }
    
    public int getRow() { return (int) position.getY(); }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public void setPosition(int col, int row) { position.setLocation(col, row); }
    
    public void move(Direction d) {

        int X_Shift = 0;
        int Y_Shift = 0;
        if (d == Direction.WEST) { X_Shift--; } 
        else if (d == Direction.EAST) { X_Shift++; } 
        else if (d == Direction.NORTH) { Y_Shift++; } 
        else { Y_Shift--; }
        
        position.setLocation(position.x + X_Shift, position.y + Y_Shift);
    }
    
    public void rotate(Element rotationPoint) {

        int col = position.y - rotationPoint.getRow();
        int row = position.x - rotationPoint.getCol();
        position.setLocation(rotationPoint.getCol() + col, rotationPoint.getRow() - row);
    }
    
    @Override
    public Element clone() throws CloneNotSupportedException {
        
        Element clone = new Element(this.getCol(), this.getRow(), this.color);
        return clone;
    }

    //Для вывода на экран
    @Override
    public String toString() { return "(" + getCol() + ", " + getRow() + ", " + getColor() + ") "; }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return position.x == element.position.x && position.y == element.position.y;
    }
}
