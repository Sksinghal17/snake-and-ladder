package snake.and.ladder.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import lombok.Getter;

@Getter
public class Game {

  private int numberOfSnakes;
  private int numberOfLadders;

  private Queue<Player> players;
  private List<Snake> snakes;
  private List<Ladder> ladders;

  private Board board;
  private Dice dice;

  public Game(int numberOfSnakes, int numberOfLadders, int boardSize) {
    this.numberOfSnakes = numberOfSnakes;
    this.numberOfLadders = numberOfLadders;
    this.players = new ArrayDeque<>();
    this.snakes = new ArrayList<>(numberOfSnakes);
    this.ladders = new ArrayList<>(numberOfLadders);
    this.board = new Board(boardSize);
    this.dice = new Dice(1, 6);
    initBoard();
  }

  private void initBoard() {
    Set<String> slSet = new HashSet<>();
    for (int i = 0; i < numberOfSnakes; i++) {
      while (true) {
        int snakeStart = getRandomNumber(board.getStart(), board.getSize());
        int snakeEnd = getRandomNumber(board.getStart(), board.getSize());
        if (snakeEnd >= snakeStart) {
          continue;
        }
        String startEndPair = String.valueOf(snakeStart) + snakeEnd;
        if (!slSet.contains(startEndPair)) {
          Snake snake = new Snake(snakeStart, snakeEnd);
          snakes.add(snake);
          slSet.add(startEndPair);
          break;
        }
      }
    }
    for (int i = 0; i < numberOfLadders; i++) {
      while (true) {
        int ladderStart = getRandomNumber(board.getStart(), board.getSize());
        int ladderEnd = getRandomNumber(board.getStart(), board.getSize());
        if (ladderEnd <= ladderStart) {
          continue;
        }
        String startEndPair = String.valueOf(ladderStart) + ladderEnd;
        if (!slSet.contains(startEndPair)) {
          Ladder ladder = new Ladder(ladderStart, ladderEnd);
          ladders.add(ladder);
          slSet.add(startEndPair);
          break;
        }
      }
    }
  }

  private int getRandomNumber(int x, int y) {

    Random random = new Random();
    return random.nextInt(y - x + 1) + x;
  }

  public void addPlayer(Player player) {
    this.players.add(player);
  }

  private int getNewPosition(int newPosition) {
    for (Snake snake : snakes) {
      if (snake.getHead() == newPosition) {
        System.out.println("snake bit");
        return snake.getTail();
      }
    }
    for (Ladder ladder : ladders) {
      if (ladder.getStart() == newPosition) {
        System.out.println("yeah ladder");
        return ladder.getEnd();
      }
    }
    return newPosition;
  }

  public void playGame() {
    while (true) {
      Player player = players.poll();
      int val = dice.roll();
      assert player != null;
      int newPosition = player.getPosition() + val;
      if (newPosition > board.getEnd()) {
        player.setPosition(player.getPosition());
        players.offer(player);
      } else {
        player.setPosition(getNewPosition(newPosition));
        if (player.getPosition() == board.getEnd()) {
          player.setWon(true);
          System.out.println("Player " + player.getName() + " Won.");
        } else {
          System.out.println(
              "Setting " + player.getName() + "'s new position to " + player.getPosition());
          players.offer(player);
        }
      }
      if (players.size() < 2) {
        break;
      }
    }
  }

}
