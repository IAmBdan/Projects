package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * class for board pannel
 */
public class BoardPanel extends JPanel implements IBoard{
  private MarbleSolitaireModelState modelState;
  private Image emptySlot, marbleSlot, blankSlot;
  private final int cellDimension;
  private int originX,originY;

  private ControllerFeatures features;

  /**
   * assings a picture to the board and creates the board
   * @param state
   * @throws IllegalStateException if the icons cant be found
   */
  public BoardPanel(MarbleSolitaireModelState state) throws IllegalStateException {
    super();
    this.modelState = state;
    this.setBackground(Color.WHITE);
    this.cellDimension = 50;
    try {
      emptySlot = ImageIO.read(new FileInputStream("res/empty.png"));
      emptySlot = emptySlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);

      marbleSlot = ImageIO.read(new FileInputStream("res/marble.png"));
      marbleSlot = marbleSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);

      blankSlot = ImageIO.read(new FileInputStream("res/blank.png"));
      blankSlot = blankSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);

      this.setPreferredSize(
              new Dimension((this.modelState.getBoardSize() + 4) * cellDimension
                      , (this.modelState.getBoardSize() + 4) * cellDimension));
    } catch (IOException e) {
      throw new IllegalStateException("Icons not found!");
    }

  }

  /**
   * overrides paint commponent method which paints the board
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    originX = (int) (this.getPreferredSize().getWidth() / 2 - this.modelState.getBoardSize() * cellDimension / 2);
    originY = (int) (this.getPreferredSize().getHeight() / 2 - this.modelState.getBoardSize() * cellDimension / 2);


    //your code to the draw the board should go here. 
    //The originX and originY is the top-left of where the cell (0,0) should start
    //cellDimension is the width (and height) occupied by every cell

    for (int i = 0; i < this.modelState.getBoardSize(); i++) {
      for (int j = 0; j < this.modelState.getBoardSize(); j++) {
        switch (this.modelState.getSlotAt(i, j)) {
          case Invalid:
            g.drawImage(blankSlot, originX + j * cellDimension, originY + i * cellDimension, null);
            break;
          case Marble:
            g.drawImage(marbleSlot, originX + j * cellDimension, originY + i * cellDimension, null);
            break;
          case Empty:
            g.drawImage(emptySlot, originX + j * cellDimension, originY + i * cellDimension, null);
        }
      }
    }
    
  }

  /**
   * ovverides the accept method
   * @param features
   */
  @Override
  public void accept(ControllerFeatures features) {

    this.features = features;
    MouseListener mouse = new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        int col = (e.getX() / cellDimension - originX / cellDimension);
        int row = (e.getY() / cellDimension - originY / cellDimension);
        features.input(row, col);
      }
    };
    this.addMouseListener(mouse);
  }
}
