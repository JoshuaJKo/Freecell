package cs3500.freecell.hw02;

import java.util.List;

/**
 * To represent a list of Foundation piles.
 */
public class Foundation extends AllPiles {
  
  public Foundation(int numPiles) {
    super(numPiles);
  }
  
  /**
   * Creates a string representation of the foundation piles.
   *
   * @return a string representation of the foundation piles.
   */
  public String toString() {
    return createPileString("F");
  }
  
  /**
   * Moves a card to the given foundation pile.
   *
   * @param toMove    The card that the player wants to move.
   * @param toPileNum The pile that the player wants to move the card to.
   */
  public void moveCard(Card toMove, int toPileNum) {
    Pile destPile = this.piles.get(toPileNum);
    
    if ((destPile.cards.isEmpty() && toMove.getValue() == CardValues.ACE)
        || ((!destPile.cards.isEmpty())
        && canMove(toMove, destPile.cards.get(destPile.cards.size() - 1)))) {
      
      destPile.cards.add(toMove);
      
    } else {
      throw new
        IllegalArgumentException("cannot place" + toMove.toString() + " card on foundation pile");
    }
  }
  
  /**
   * Checks if a card is stackable on another card by the rules of the foundation pile.
   *
   * @param toMove Card that the player wants to move onto the bottom of a foundation stack
   * @param bottom the card this card is trying to stack itself onto.
   * @return true if the card is one more and the same suit than the card given.
   */
  private boolean canMove(Card toMove, Card bottom) {
    return bottom.isOneLess(toMove) && toMove.getSuit() == bottom.getSuit();
  }
  
  
  /**
   * Override of game-over check checking if all the foundation piles have 13 cards and the last
   * card in each pile is a king.
   *
   * @return whether all the foundation piles are full
   */
  @Override
  boolean isGameOver() {
    for (Pile p : this.piles) {
      
      List<Card> toCheckPile = p.getCards();
      
      if (toCheckPile.isEmpty() || toCheckPile.size() != 13) {
        return false;
      }
    }
    
    return true;
  }
}