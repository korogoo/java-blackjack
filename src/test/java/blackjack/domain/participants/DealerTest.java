package blackjack.domain.participants;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Rank;
import blackjack.domain.card.ShuffledDeck;
import blackjack.domain.card.Suit;
import blackjack.domain.participants.state.Stay;
import java.util.List;
import org.junit.jupiter.api.Test;

class DealerTest {
    @Test
    void 점수가_임계점을_초과하면_스탠드한다() {
        // given
        List<Card> cards = List.of(
            new Card(Rank.TEN, Suit.CLOVER),
            new Card(Rank.TEN, Suit.CLOVER),
            new Card(Rank.TEN, Suit.CLOVER));
        Deck deck = new ShuffledDeck(cards);

        Dealer dealer = Dealer.createEmptyHand();
        // when
        int hitCount = dealer.playTurn(deck);
        // then
        assertThat(hitCount).isEqualTo(2);
        assertThat(dealer.state).isInstanceOf(Stay.class);
    }
}
