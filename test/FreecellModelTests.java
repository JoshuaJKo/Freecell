import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.CardValues;
import cs3500.freecell.hw02.CardSuits;
import cs3500.freecell.hw02.AllPiles;
import cs3500.freecell.hw02.Cascade;
import cs3500.freecell.hw02.Foundation;
import cs3500.freecell.hw02.Open;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class the test all of the functionality of a freecell game.
 */
public class FreecellModelTests {
  private FreecellModel gameOne;
  List<Card> deckOne;
  
  @Test
  public void testGetDeck() {
    FreecellModel gameOne = new FreecellModel();
    deckOne = gameOne.getDeck();
    
    assertEquals(deckOne.size(), 52);
  }
  
  
  @Test
  public void testGameState() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 8, 4, false);
    String gameState = gameOne.getGameState();
    String expected = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C2: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C3: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C6: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C7: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    
    assertEquals(gameState, expected);
  }
  
  @Test(expected = IllegalStateException.class)
  public void testIllegalStateExceptionGetCard() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.getCard(PileType.CASCADE, 5, 10);
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBoundsExceptionGetCard() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 7, 3, false);
    gameOne.getCard(PileType.CASCADE, 20, 10);
  }
  
  @Test
  public void testGetCard() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 7, 3, false);
    assertEquals(new Card(CardValues.NINE, CardSuits.SPADE),
        gameOne.getCard(PileType.CASCADE, 0, 5));
  }
  
  @Test
  public void testIsGameOver() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 52, 4, false);
    gameOne.startGame(gameOne.getDeck(), 52, 4, false);
    
    for (int i = 0; i < 52; i++) {
      gameOne.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    
    System.out.println(gameOne.getGameState());
    assertTrue(gameOne.isGameOver());
  }
  
  @Test
  public void testIsGameOverTwo() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 52, 4, false);
    gameOne.startGame(gameOne.getDeck(), 52, 4, false);
    
    for (int i = 0; i < 51; i++) {
      gameOne.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    
    System.out.println(gameOne.getGameState());
    assertFalse(gameOne.isGameOver());
  }
  
  @Test(expected = IllegalStateException.class)
  public void testIllegalStateExceptionGetMove() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.move(PileType.CASCADE, 2, 2, PileType.OPEN, 2);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionGetMove() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 7, 3, false);
    gameOne.move(PileType.CASCADE, 2, 2, PileType.OPEN, 10);
  }
  
  @Test
  public void testMove() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 7, 3, false);
    gameOne.move(PileType.CASCADE, 2, 6, PileType.OPEN, 0);
    Card firstOpen = (PileType.getPileFromModel(gameOne, PileType.OPEN))
        .getPiles().get(0).getCards().get(0);
    assertEquals(firstOpen, new Card(CardValues.QUEEN, CardSuits.CLUB));
  }
  
  //tests if the amouont of open piles is too low
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionStartGameOne() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 7, 0, false);
  }
  
  //test if the amount of cascade piles is too low
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionStartGameTwo() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 3, 2, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionStartGameThree() {
    FreecellModel gameOne = new FreecellModel();
    deckOne = new ArrayList<>();
    deckOne.add(new Card(CardValues.EIGHT, CardSuits.CLUB));
    deckOne.add(new Card(CardValues.NINE, CardSuits.CLUB));
    deckOne.add(new Card(CardValues.TEN, CardSuits.CLUB));
    gameOne.startGame(deckOne, 5, 4, false);
  }
  
  @Test
  public void testStartGame() {
    FreecellModel gameOne = new FreecellModel();
    gameOne.startGame(gameOne.getDeck(), 8, 4, false);
    
    assertEquals(PileType.getPileFromModel(gameOne, PileType.OPEN).getPiles().size(), 4);
    assertEquals(PileType.getPileFromModel(gameOne, PileType.CASCADE).getPiles().size(), 8);
    assertEquals(PileType.getPileFromModel(gameOne, PileType.FOUNDATION)
        .getPiles().size(), 4);
    
    assertEquals(PileType.getPileFromModel(gameOne, PileType.CASCADE).getPiles()
        .get(0).getCards().size(), 7);
    assertEquals(PileType.getPileFromModel(gameOne, PileType.CASCADE).getPiles()
        .get(7).getCards().size(), 6);
  }
  
  @Test
  public void testAllPiles() {
    AllPiles cascade = new Cascade(5);
    AllPiles open = new Open(5);
    AllPiles foundation = new Foundation(5);
    
    assertEquals(cascade.getPiles().size(), 5);
    assertEquals(open.getPiles().size(), 5);
    assertEquals(foundation.getPiles().size(), 5);
  }
}
