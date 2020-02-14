package cs3500.freecell.hw02;

/**
 * represents possible symbols on a card and their corresponding value.
 */
public enum CardValues {
  ACE("A", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13);
  
  private final int numericVal;
  private final String symbol;
  
  
  CardValues(String symbol, int numericVal) {
    this.symbol = symbol;
    this.numericVal = numericVal;
  }
  
  public String getSymbol() {
    return symbol;
  }
  
  public int getNumericValue() {
    return numericVal;
  }
}
