package cs3500.freecell.hw02;

/**
 * represents all the open piles in a free cell game.
 */
public class Open extends AllPiles {
  
  public Open(int numPiles) {
    super(numPiles);
  }
  
  /**
   * creates a string representation of the Open piles.
   * @return a string representation of the Open piles.
   */
  public String toString() {
    return createPileString("O");
  }
  
  /**
   * Moves a card to an open pile if it is empty.
   *
   * @param toMove the card that the player wants to move.
   * @param toPileNum the number of the pile that the player wants to move it to.
   * @throws IllegalArgumentException if the pile is not empty.
   */
  public void moveCard(Card toMove, int toPileNum) {
    Pile destination = this.piles.get(toPileNum);
    
    if (!destination.cards.isEmpty()) {
      throw new IllegalArgumentException("There already is a card in the open pile");
    }
  
    destination.cards.add(toMove);
  }
}