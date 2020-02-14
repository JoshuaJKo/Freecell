package cs3500.freecell.hw02;

import java.util.Objects;

/**
 * Represents a single card in a playing deck with a suit, value, and color.
 */
public class Card {
  
  private final CardSuits suit;
  private final CardValues value;
  private final String color;
  
  /**
   * Constructs a card with the given value and suit.
   *
   * @param value the value of a playing card
   * @param suit the suit of a playing card
   */
  public Card(CardValues value, CardSuits suit) {
    this.value = value;
    this.suit = suit;
    
    if (this.suit == CardSuits.SPADE || this.suit == CardSuits.CLUB) {
      this.color = "BLACK";
    }
    else {
      this.color = "RED";
    }
  }
  
  public CardValues getValue() {
    return value;
  }
  
  public String getColor() {
    return color;
  }
  
  public CardSuits getSuit() {
    return suit;
  }
  
  
  /**
   * compares to cards to see if they're equal.
   * @param toCompare the other card to compare this card to.
   * @return true if both cards contain the same values.
   */
  @Override
  public boolean equals(Object toCompare) {
    if (this == toCompare) {
      return true;
    }
    else if (toCompare instanceof Card) {
      Card cardCompare = (Card) toCompare;
      return this.value == cardCompare.value && this.suit == cardCompare.suit;
    }
    else {
      return false;
    }
  }
  
  /**
   * Creates a unique code based on the suit and value of a card.
   * @return a unique code based on the suit and value of the card.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.suit) + Objects.hash((this.value));
  }
  
  /**
   * Creates a string representation of this card.
   * @return a string representation of this card.
   */
  public String toString() {
    return this.value.getSymbol() + this.suit.getSymbol();
  }
  
  public boolean isOneLess(Card toCompare) {
    return this.value.getNumericValue() == toCompare.value.getNumericValue() - 1;
  }
}