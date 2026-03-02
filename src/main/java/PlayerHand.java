import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private final List<Integer> cardValues;

    public PlayerHand(List<Integer> cardValues) {
        this.cardValues = cardValues;
    }

    public List<Integer> getCardValues() {
        List<Integer> copiedCardValues = new ArrayList<>(this.cardValues);
        int sum = copiedCardValues.stream().mapToInt(x -> x).sum();
        if (!copiedCardValues.contains(1) || sum > 11) {
            return copiedCardValues;
        }
        int firstAceIndex = copiedCardValues.indexOf(1);
        copiedCardValues.set(firstAceIndex, 11);
        return copiedCardValues;
    }
}
