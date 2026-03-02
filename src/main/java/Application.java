import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(1, 1, 1, 8));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();
        System.out.println(adjusted);
    }
}
