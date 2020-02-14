package cs3500.freecell.hw02;

/**
 * represents possible suits that a card can have.
 */
public enum CardSuits {
  DIAMOND('♦'),
  SPADE('♠'),
  HEART('♥'),
  CLUB('♣');
  
  private final char symbol;
  
  CardSuits(char symbol) {
    this.symbol = symbol;
  }
  
  public char getSymbol() {
    return symbol;
  }
}
