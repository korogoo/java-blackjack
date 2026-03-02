import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest {
    @Test
    @DisplayName("1(ace)가 1장 포함 되어있고, 총 합이 11을 초과하는 경우 → ace 카드는 1로 처리한다.")
    void test1() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(5, 10, 1));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(cardValues);
    }

    @Test
    @DisplayName("1(ace)가 2장 이상 포함 되어있고, 총 합이 11을 초과하는 경우 → 모든 ace 카드를 1로 처리한다.")
    void test2() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(1, 1, 10));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(cardValues);
    }

    @Test
    @DisplayName("1(ace)이 2장 이상 포함 되어있고, 총 합이 11을 초과하는 경우 → 모든 ace 카드를 1로 처리한다.")
    void test3() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(1, 1, 1, 9));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(cardValues);
    }

    @Test
    @DisplayName("1(ace)이 1장 포함 되어있고, 총 합이 10 이하인 경우 → ace 카드를 11로 처리한다.")
    void test4() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(5, 2, 1));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(new ArrayList<>(Arrays.asList(5, 2, 11)));
    }

    @Test
    @DisplayName("1(ace)이 2장 이상 포함 되어있고, 총 합이 10 이하인 경우 → 첫 ace 카드 하나를 11로 처리한다.")
    void test5() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(1, 1, 1, 8));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(new ArrayList<>(Arrays.asList(11, 1, 1, 8)));
    }

    @Test
    @DisplayName("카드에 1(ace)이 포함되지 않은 경우 → 카드 리스트 그대로 반환한다.")
    void test6() {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(2, 10));
        PlayerHand playerHand = new PlayerHand(cardValues);
        List<Integer> adjusted = playerHand.getCardValues();

        assertThat(adjusted).isEqualTo(new ArrayList<>(Arrays.asList(2, 10)));
    }

    @ParameterizedTest
    @DisplayName("초기화 카드 값의 범위가 1~10 를 벗어나는 경우 → 에러를 던진다.")
    @ValueSource(ints = {0, 11})
    void test7(int cardValue) {
        List<Integer> cardValues = new ArrayList<>(Arrays.asList(cardValue, 10));
        assertThatThrownBy(() -> new PlayerHand(cardValues))
            .hasMessageContaining("범위");
    }
}