package my_tetris.view;

import my_tetris.Direction;
import my_tetris.Game;
import my_tetris.events.GameEvent;
import my_tetris.events.GameListener;
import my_tetris.general_game_objects.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

public class GamePanel extends JFrame{

    private final int GLASS_HEIGHT = 20;

    private final int GLASS_WIDTH = 10;

    private final int NEXT_SHAPE_SPACE_HEIGHT = 2;

    private final int NEXT_SHAPE_SPACE_WIDTH = 4;

    private ElementsPanel glassField = new ElementsPanel(GLASS_HEIGHT, GLASS_WIDTH);

    private ElementsPanel nextShapeSpace = new ElementsPanel(NEXT_SHAPE_SPACE_HEIGHT, NEXT_SHAPE_SPACE_WIDTH);

    private GlassController glassController = new GlassController();

    private ShapeController shapeController = new ShapeController();
    
    private NextShapeController nextShapeController = new NextShapeController();
    
    private KeyAdapter keyAdapter =  new java.awt.event.KeyAdapter() {

        public void keyPressed(java.awt.event.KeyEvent evt) {
            glassPanelKeyPressed(evt);
        }
    };
    
    private Game game = new Game();

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        
        shapeController.setElementsPanel(glassField);
        glassController.setElementsPanel(glassField);
        nextShapeController.setElementsPanel(nextShapeSpace);
        nextShapeController.setShapeFactory(game.getShapeFactory());
        
        game.addGameListener(new GameObserver());
        initComponents();
    }

    public Game getGame() { return game; }

    private void initComponents() {

        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        content = (JPanel) this.getContentPane();
        content.setPreferredSize(new Dimension(360,400));
        setResizable(false);

        content.add(glassField, BorderLayout.WEST);

        leftSidePanel = new JPanel();
        leftSidePanel.setPreferredSize(new Dimension(150, 380));
        leftSidePanel.setLayout(new FlowLayout());

        scoreLabel = new JLabel("Счет");
        scoreLabel.setPreferredSize(new Dimension(120, 25));
        leftSidePanel.add(scoreLabel);

        scoreField = new JTextField();
        scoreField.setEditable(false);
        scoreField.setFocusable(false);
        scoreField.setPreferredSize(new Dimension(130, 25));
        leftSidePanel.add(scoreField);

        var emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(150, 120));
        leftSidePanel.add(emptyPanel);

        nextShapeLabel = new JLabel("    Следующая фигура");
        nextShapeLabel.setPreferredSize(new Dimension(150, 25));
        leftSidePanel.add(nextShapeLabel);

        leftSidePanel.add(nextShapeSpace);

        emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(150, 90));
        leftSidePanel.add(emptyPanel);

        startGameButton = new JButton("Начать игру");
        startGameButton.setPreferredSize(new Dimension(120, 25));
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });
        leftSidePanel.add(startGameButton);

        content.add(leftSidePanel, BorderLayout.EAST);
        pack();
        setVisible(true);
        setFocusable(true);
    }

    private JPanel content;
    private JPanel leftSidePanel;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel nextShapeLabel;
    private javax.swing.JTextField scoreField;

    //Слушает нажатие кнопок и по ним сообщает игре, как нужно переместить/повернуть фигуру
    private void glassPanelKeyPressed(java.awt.event.KeyEvent evt) {

        if (game != null && game.isInProgress()) {
            if (evt.getKeyCode() == evt.VK_RIGHT) {
                shapeController.getShape().move(Direction.EAST);
            } else if (evt.getKeyCode() == evt.VK_LEFT) {
                shapeController.getShape().move(Direction.WEST);
            }  else if (evt.getKeyCode() == evt.VK_DOWN) {
                shapeController.getShape().move(Direction.SOUTH);
            } else if (evt.getKeyCode() == evt.VK_UP) {
                shapeController.getShape().rotate();
            }

        }
    }

    private void bStartActionPerformed(java.awt.event.ActionEvent evt) {

        startGameButton.setEnabled(false);
        addKeyListener(keyAdapter);
        game.start();
    }


    //Слушает события от игры, изменилась ли обстановка, заполнился ли стакан, какой текущий счет
    private class GameObserver implements GameListener {

        @Override
        public void gameFinished() {

            startGameButton.setEnabled(true);
            String str = "Игра закончилась.\nВаш счет: " + game.getScore();
            JOptionPane.showMessageDialog(null, str, "Конец игры", JOptionPane.INFORMATION_MESSAGE);
            removeKeyListener(keyAdapter);
        }

        @Override
        public void scoreChanged() {
            scoreField.setText(game.getScore() + "");
        }

        @Override
        public void glassWasSetup(GameEvent e) { glassController.setGlass(e.getNewGlass()); }

        @Override
        public void activeShapeChanged(GameEvent e) { shapeController.setShape(e.getNewShape()); }

    }

}
