package cs3500.freecell.hw02;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * represents all the functional aspects of a full game of freecell solitare where an entire game
 * can be played.
 */
public class FreecellModel implements FreecellOperations<Card> {
  
  private List<Card> deck;
  private  Cascade cascade;
  private  Foundation foundation;
  private  Open opens;
  private  boolean hasGameStarted;
  
  /**
   * No parameter constructor that sets all piles to 0/empty and is considered an unstarted game.
   */
  public FreecellModel() {
    this.deck = new ArrayList<>();
    this.cascade = new Cascade(0);
    this.foundation = new Foundation(0);
    this.opens = new Open(0);
    hasGameStarted = false;
  }
  
  /**
   * Return a valid and complete deck of cards for a game of Freecell.
   *
   * @return the deck of cards as a list.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    
    for (CardValues value : CardValues.values()) {
      deck.add(new Card(value, CardSuits.CLUB));
      deck.add(new Card(value, CardSuits.HEART));
      deck.add(new Card(value, CardSuits.DIAMOND));
      deck.add(new Card(value, CardSuits.SPADE));
    }
    
    return deck;
  }

  public Cascade getCascade() {
    return cascade;
  }
  
  public Open getOpen() {
    return opens;
  }
  
  public Foundation getFoundation() {
    return foundation;
  }
  
  
  /**
   * Deal a new game of freecell with the given deck, with or without shuffling
   * it first.
   *
   * @param numCascadePiles number of cascade piles.
   * @param numOpenPiles    number of open piles.
   * @param deck            the deck to be dealt.
   * @param shuffle         if true, shuffle the deck else deal the deck as-is.
   * @throws IllegalArgumentException if the number of cascade piles or number of open piles is
   *                                  an invalid number.
   * @throws IllegalArgumentException if the deck is invalid.
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    
    if (numOpenPiles < 1 || numCascadePiles < 4) {
      throw new IllegalArgumentException("Must have at least 1 open pile and 4 cascade piles");
    }
  
    this.deck = new ArrayList<>();
    for (Card toAdd : deck) {
      this.deck.add(toAdd);
    }
    
    if (isInvalid(this.deck)) {
      throw new IllegalArgumentException("Invalid deck");
    }
    
    if (shuffle) {
      Collections.shuffle(this.deck);
    }
    
    this.foundation = new Foundation(4);
    this.opens = new Open(numOpenPiles);
    this.cascade = new Cascade(numCascadePiles);
    
    for (int i = 0; i < this.deck.size(); i++) {
      Pile addPile = this.cascade.piles.get(i % this.cascade.piles.size());
      addPile.cards.add(this.deck.get(i));
    }
  
    /*I reversed it in order to make it easier to test because the lowest cards
    are at the front of the pile
    for(int j=0; j<this.cascade.piles.size(); j++){
      Collections.reverse(this.cascade.piles.get(j).getCards());
    }*/
    
    hasGameStarted = true;
  }
  
  /**
   * Moves a card to the given destination pile.
   *
   * @param source         the type of the source pile see {@link PileType}.
   * @param pileNumber     the pile number of the given type, starting at 0.
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0.
   * @param destination    the type of the destination pile (see {@link PileType}).
   * @param destPileNumber the pile number of the given type, starting at 0.
   * @throws IllegalStateException when the game has not started yet
   * @throws IllegalArgumentException when the move is impossible
   */
  @Override
  public void move(PileType source,
                   int pileNumber, int cardIndex, PileType destination, int destPileNumber) {
    
    if (!hasGameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    
    AllPiles allDestPiles = PileType.getPileFromModel(this, destination);
    
    if (destPileNumber >= allDestPiles.piles.size() || destPileNumber < 0) {
      throw new IllegalArgumentException("Destination Pile number is not in range");
    }
    
    //moves the card over to the destination pile then removes it from the source pile
    allDestPiles.moveCard(getCard(source, pileNumber, cardIndex), destPileNumber);
    PileType.getPileFromModel(this, source).piles.get(pileNumber).cards.remove(cardIndex);
  }
  
  /**
   * Checks if the game is over.
   *
   * @return true if open and cascades is empty and every foundation pile is in complete order.
   */
  @Override
  public boolean isGameOver() {
    if (!hasGameStarted) {
      return false;
    }
    
    return this.opens.isGameOver() && this.foundation.isGameOver() && this.cascade.isGameOver();
  }
  
  /**
   * Returns the card at the given index in the specified pile.
   *
   * @param pile       the type of the pile.
   * @param pileNumber the pile number of the given type, starting from 0.
   * @param cardIndex  the index of the card, starting from 0.
   * @return card that was requested from the pile and the index of the pile.
   */
  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    
    if (!hasGameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    
    AllPiles toRetrivePiles = PileType.getPileFromModel(this, pile);
    
    if (pileNumber >= toRetrivePiles.piles.size()) {
      throw new IndexOutOfBoundsException("Invalid pile index.");
    }
    
    return toRetrivePiles.piles.get(pileNumber).cards.get(cardIndex);
  }
  
  /**
   * formats the present state of the game as a string.
   *
   * @return a String that represents the current state of the game.
   */
  @Override
  public String getGameState() {
    String gameState = this.foundation.toString()
        + this.opens.toString()
        + this.cascade.toString();
    
    //removes the extra \n at the end
    if (hasGameStarted) {
      gameState = gameState.substring(0, gameState.length() - 1);
    }
    
    return gameState;
  }
  
  /**
   * Checks if a given deck is invalid.
   *
   * @param deck a deck full of cards.
   * @return true if the deck has 52 cards and no duplicates.
   */
  private boolean isInvalid(List<Card> deck) {
    if (deck.size() != 52) {
      return true;
    }
    
    //uses a hashset to check if there are duplicates
    Set<Card> set = new HashSet<Card>(deck);
    return (set.size() < deck.size());
  }
}
