package cs3500.freecell.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellOperations;

/**
 * Represents a controller in a game of freecell. Takes in user input then conducts and outputs
 * a game based on those inputs.
 */
public class FreecellController implements IFreecellController<Card> {
  private Scanner sc;
  private Appendable ap;
  
  /**
   * Constructs a Freecell Controller with a Readable object to read the inputs of the user
   * and an Appendable object that will output the results and any issues to the user.
   *
   * @param rd A Readable object that will handle the input.
   * @param ap An Appendable object that will handle the output.
   * @throws IllegalArgumentException if rd or ap are null (non-intialized).
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (ap == null || rd == null) {
      throw new IllegalArgumentException("Readable or Appendable has not been initialized.");
    }
    
    this.sc = new Scanner(rd);
    this.ap = ap;
  }
  
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
  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
                       int numOpens, boolean shuffle) {
    
    //checks to see if the parameters given are valid
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or deck is null.");
    }
    
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      appendIOCatch("Could not start game.");
      return;
    }
    
    //continuously runs the game until the user quits or the game is over
    while (!model.isGameOver()) {
      
      if (!sc.hasNext()) {
        throw new IllegalStateException("Readable is empty");
      }
      
      appendIOCatch('\n' + model.getGameState() + '\n');
      
      try {
        
        //checks if the player has quit between any inputs to see if the loop should break
        if (hasPlayerQuit()) {
          return;
        }
        
        String pileTypeLine = sc.next();
        PileType sourcePileType = getPileTypeFromString(pileTypeLine);
        int sourcePileNumber = Integer.parseInt(pileTypeLine.substring(1));
        
        /*
        Here is where I attempted to vet bad input specifically for the source pile string
        try {
          model.getCard(sourcePileType, sourcePileNumber, 0);
        } catch (IllegalArgumentException e) {
          appendIOCatch("\nInvalid Source Pile Index, try again.");
        }*/
        
        if (hasPlayerQuit()) {
          return;
        }
        
        String cardIndexLine = sc.next();
        int cardIndex = Integer.parseInt(cardIndexLine);
        
        /*
        Here is where I attempted to vet bad input specifically for the source card index
        try {
          model.getCard(sourcePileType, sourcePileNumber, cardIndex);
        } catch (IllegalArgumentException e) {
          appendIOCatch("\nInvalid Source Card Index, try again.");
        }*/
        
        if (hasPlayerQuit()) {
          return;
        }
        
        String destinationPileLine = sc.next();
        PileType destinationPileType = getPileTypeFromString(destinationPileLine);
        int destinationPileNumber = Integer.parseInt(destinationPileLine.substring(1));
        
        /*
          Here is where I attempted to vet bad input specifically for the destination pile
        if (destPileNumber < 0
          || (destinationPileLine.charAt(0) == 'O' && destinationPileNumber > numOpens)
          || (destinationPileLine.charAt(0) == 'C' && destinationPileNumber > numCascades)
          || (destinationPileLine.charAt(0) == 'F' && destinationPileNumber > 4)) {
          appendIOCatch("\nInvalid destination pile, try again.");
        }*/
        
        //attempts to make the move and catches if any of the inputs were out of bounds
        try {
          model.move(sourcePileType, sourcePileNumber - 1, cardIndex - 1,
              destinationPileType, destinationPileNumber - 1);
        } catch (IndexOutOfBoundsException e) {
          appendIOCatch("\nOne or more invalid indexes, try again.\n");
        }
        
        //ended up only being able to group catch all the invalid moves
      } catch (IllegalArgumentException e) {
        appendIOCatch("\nInvalid move, try again.\n");
      }
    }
    
    appendIOCatch('\n' + model.getGameState() + '\n');
    
    if (model.isGameOver()) {
      appendIOCatch("\nGame over.");
    }
  }
  
  /**
   * Gets the pile type from the corrisponding character of the string.
   *
   * @param toGetFrom a String that a pile type will be derived from.
   * @return a PileType that matches the first character.
   * @throws IllegalArgumentException if none of the characters match 'O', 'C', or 'F'.
   */
  private PileType getPileTypeFromString(String toGetFrom) {
    char c = toGetFrom.charAt(0);
    switch (c) {
      case 'O':
        return PileType.OPEN;
      case 'F':
        return PileType.FOUNDATION;
      case 'C':
        return PileType.CASCADE;
      default:
        throw new IllegalArgumentException("String does not corrispond to a pile type");
    }
  }
  
  /**
   * Appends the given string onto the Appendable.
   *
   * @param toAppend the string that should be appended.
   */
  private void appendIOCatch(String toAppend) {
    try {
      ap.append(toAppend);
    } catch (IOException o) {
      throw new IllegalStateException("Appendable not working");
    }
  }
  
  
  /**
   * Checks to see if the player quits on the next line of the reader.
   *
   * @return true if the player inputs 'Q' or 'q', false otherwise.
   */
  private boolean hasPlayerQuit() {
    if (sc.hasNext("Q") || sc.hasNext("q")) {
      appendIOCatch("\nGame quit prematurely.\n");
      return true;
    }
    return false;
  }
}
