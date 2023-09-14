package cs3500.marblesolitaire.view;


import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.controller.GUIController;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * class for gui driver for the gui
 */
public class GUIDriver {
  public static void main(String[] args) {
    MarbleSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireGuiView view = new SwingGuiView(model);
    ControllerFeatures controller = new GUIController(model, view);
    view.acceptController(controller);

  }
}