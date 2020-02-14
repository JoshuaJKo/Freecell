package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pile of cards.
 */
public class Pile {
  List<Card> cards;
  
  Pile() {
    this.cards = new ArrayList<Card>();
  }
  
  public List<Card> getCards() {
    return this.cards;
  }
}