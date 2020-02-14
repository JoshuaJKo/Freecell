package cs3500.freecell.hw02;

/**
 * Represents all cascade piles in a game of freecell.
 */
public class Cascade extends AllPiles {
  
  public Cascade(int numPiles) {
    super(numPiles);
  }
  
  /**
   * creates a string representation of the cascade piles.
   * @return a string representation of the cascade piles.
   */
  public String toString() {
    return createPileString("C");
  }
  
  /**
   * Moves a card to an available cascade pile if the card is stackable onto the pile.
   *
   * @param toMove    the card that the player wants to move.
   * @param toPileNum the number of the pile that the player wants to move it to.
   * @throws IllegalArgumentException if the card is not one value below the bottom card or the
   *                                  cards are not different colors.
   */
  public void moveCard(Card toMove, int toPileNum) {
    
    Pile destPile = this.piles.get(toPileNum);
  
    if (destPile.cards.isEmpty()
        || (canMove(toMove, destPile.cards.get((destPile.cards.size() - 1))))) {
      
      destPile.cards.add(toMove);
      
    }
    else {
      throw new
        IllegalArgumentException("Invalid move onto cascade pile");
    }
  }
  
  /**
   * Checks if a card is stackable on another card by the rules of the cascade pile.
   *
   * @param toMove Card that the player wants to move onto the bottom of a foundation stack
   * @param bottom the card this card is trying to stack itself onto.
   * @return true if the card is one more and the same suit than the card given.
   */
  private boolean canMove(Card toMove, Card bottom) {
    return toMove.isOneLess(bottom)
      && toMove.getColor() != bottom.getColor();
  }
}