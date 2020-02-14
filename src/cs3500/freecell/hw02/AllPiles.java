package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that represents a pile.
 */
public abstract class AllPiles {
  
  List<Pile> piles;
  
  /**
   * Constucts a pile with the given type.
   *
   * @param numPiles the number of piles in that type of pile.
   */
  AllPiles(int numPiles) {
    piles = new ArrayList<Pile>();
    
    for (int i = numPiles; i > 0; i--) {
      piles.add(new Pile());
    }
  }
  
  public List<Pile> getPiles() {
    return piles;
  }
  
  /**
   * checks if the game is over.
   *
   * @return true if the pile is empty, false otherwise.
   */
  boolean isGameOver() {
    for (int i = 0; i > piles.size(); i++) {
      if (piles.get(i).cards.isEmpty()) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Moves a card to a pile depending on its pile type.
   *
   * @param toMove    The card that the player wants to move.
   * @param toPileNum The pile that the player wants to move the card to.
   */
  public abstract void moveCard(Card toMove, int toPileNum);
  
  /**
   * Creates a string that represents the game state. Displays each pile and the cards inside
   * each pile.
   *
   * @param pileType takes in the type of pile to print before the cards
   * @return a string with each pile and the cards in each pile
   */
  String createPileString(String pileType) {
    
    String gameString = "";
    
    //iterates through all the piles
    for (int i = 0; i < piles.size(); i++) {
      Pile toPrintPile = piles.get(i);
      
      gameString += pileType + (i + 1) + ":";
      
      //prints out each card in the pile
      for (int j = 0; j < toPrintPile.cards.size(); j++) {
        Card currCard = toPrintPile.cards.get(j);
        
        gameString += " " + currCard.toString();
        
        if (j != toPrintPile.cards.size() - 1) {
          gameString += ",";
        }
      }
      gameString += "\n";
    }
    return gameString;
  }
}