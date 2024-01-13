package snake.and.ladder.model;


import lombok.Getter;
import lombok.Setter;

@Getter
public class Player {

  private final String name;
  @Setter
  private int position;
  @Setter
  private boolean won;

  public Player(String name) {
    this.name = name;
    this.position = 0;
    this.won = false;
  }
}
