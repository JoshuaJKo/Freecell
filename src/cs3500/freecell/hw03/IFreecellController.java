package cs3500.freecell.hw03;

import java.util.List;

import cs3500.freecell.hw02.FreecellOperations;

/**
 * Represents an interface for a controller for a game of freecell.
 *
 * @param <K> For implimentation so far this will represent cards in a deck
 */
public interface IFreecellController<K> {
  
  /**
   * Creates a plays a game of freecell. The function will check to see if all the parameters
   * given are valid then go through a game until the game is completed or the user quits.
   *
   * @param deck        a valid 52-card deck of playing cards.
   * @param model       a model of the freecell game used to conduct all the operations of the game.
   * @param numCascades the number of cascade piles in the game (Minimum of 4).
   * @param numOpens    the number of open piles in the game (Minimum of 1).
   * @param shuffle     true if the user wants to shuffle the deck, false otherwise.
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades, int numOpens,
                boolean shuffle);
}