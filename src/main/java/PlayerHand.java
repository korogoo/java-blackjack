import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private final List<Integer> cardValues;

    public PlayerHand(List<Integer> cardValues) {
        validateBounds(cardValues);
        this.cardValues = cardValues;
    }

    private static void validateBounds(List<Integer> cardValues) {
        if (cardValues.stream().anyMatch(x -> x > 10 || x < 1)) {
            throw new IllegalArgumentException("[ERROR] 카드값의 범위는 1부터 10사이의 정수입니다. (ace 조정 제외)");
        }
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
