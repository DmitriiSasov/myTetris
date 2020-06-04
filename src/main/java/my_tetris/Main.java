package my_tetris;

import my_tetris.general_game_objects.AbstractShape;
import my_tetris.general_game_objects.Shape;
import my_tetris.general_game_objects.SinkingShape;
import my_tetris.view.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("FlatLaf Light".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GamePanel gp = new GamePanel();
                setNewShapeType(gp.getGame().getShapeFactory());
                gp.setVisible(true);
            }
        });
    }
    
    private static void setNewShapeType(ShapeFactory sf) {

        int frequency = 2;
        AbstractShape shape = new SinkingShape(5);
        ArrayList<ElementsMatrix> shapeForms = new ArrayList<>();

        ElementsMatrix shapeElements = new ElementsMatrix(1);
        //Создаем фигуру I из 3-х
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeForms.add(shapeElements);

        //Создаем фигуру L из 3-х элементов
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(3, 0)), 0);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeForms.add(shapeElements);

        //Создаем фигуру С
        shapeElements = new ElementsMatrix(2);
        shapeElements.add(new Element(new Point(3, 1)), 1);
        shapeElements.add(new Element(new Point(1, 0)), 0);
        shapeElements.add(new Element(new Point(3, 0)), 0);        
        shapeElements.add(new Element(new Point(2, 0)), 0);        
        shapeElements.add(new Element(new Point(1, 1)), 1);
        shapeForms.add(shapeElements);


        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(144,0,255));
        colors.add(new Color(0,255,212));
        colors.add(new Color(65, 105, 225));
        colors.add(new Color(4,188,254));
        colors.add(new Color(254,0,150));

        ShapeInfo newTemplate = new ShapeInfo(frequency, shape, shapeForms, colors);
        sf.addShapeTemplate(newTemplate);
    }
    
}
