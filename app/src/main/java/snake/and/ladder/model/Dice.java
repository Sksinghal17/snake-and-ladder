package snake.and.ladder.model;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class Dice {

  @Getter
  private int minValue;
  @Getter
  private int maxValue;
  private Random random;

  public Dice(int minValue, int maxValue) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    random = new Random();
  }

  public int roll() {
    return random.nextInt(maxValue - minValue + 1) + minValue;
  }


}
