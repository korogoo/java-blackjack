package blackjack.domain.participants.state;

import static org.assertj.core.api.Assertions.*;

import blackjack.domain.card.Card;
import blackjack.domain.card.Rank;
import blackjack.domain.card.Suit;
import blackjack.domain.participants.Hand;
import java.util.List;
import org.junit.jupiter.api.Test;

class StateTest {

    @Test
    void Hit_상태에서_stay_하면_Stay_상태로_전이한다() {
        // given
        State state = new Hit(hand(
            card(Rank.TWO, Suit.SPADE),
            card(Rank.THREE, Suit.HEART)
        ));
        // when
        State next = state.stay();
        // then
        assertThat(next).isInstanceOf(Stay.class);
    }

    @Test
    void Hit_상태에서_카드를_받아_21초과가_되면_Bust_상태로_전이한다() {
        // given
        State state = new Hit(hand(
            card(Rank.KING, Suit.SPADE),
            card(Rank.QUEEN, Suit.HEART)
        ));
        // when
        State next = state.draw(card(Rank.TWO, Suit.DIAMOND));
        // then
        assertThat(next).isInstanceOf(Bust.class);
    }

    @Test
    void Hit_상태에서_21이_되면_Blackjack_상태로_전이한다() {
        // given
        State state = new Hit(hand(
            card(Rank.ACE, Suit.SPADE)
        ));
        // when
        State next = state.draw(card(Rank.KING, Suit.HEART));
        // then
        assertThat(next).isInstanceOf(Blackjack.class);
    }

    @Test
    void Running_상태에서는_profit_계산이_불가능하다() {
        // given
        State state = new Hit(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.SEVEN, Suit.HEART)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.NINE, Suit.CLOVER),
            card(Rank.SEVEN, Suit.DIAMOND)
        ));
        // when & then
        assertThatThrownBy(() -> state.profit(1000L, dealer))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 플레이어가_버스트면_배팅금액만큼_잃는다() {
        // given
        Finished player = new Bust(hand(
            card(Rank.KING, Suit.SPADE),
            card(Rank.QUEEN, Suit.HEART),
            card(Rank.TWO, Suit.DIAMOND)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.NINE, Suit.CLOVER),
            card(Rank.SEVEN, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 딜러가_버스트면_배팅금액만큼_얻는다() {
        // given
        Finished player = new Stay(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.SEVEN, Suit.HEART)
        ));
        Finished dealer = new Bust(hand(
            card(Rank.KING, Suit.CLOVER),
            card(Rank.QUEEN, Suit.DIAMOND),
            card(Rank.TWO, Suit.HEART)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(1000L);
    }

    @Test
    void 플레이어가_블랙잭이면_배팅금액의_1_5배를_얻는다() {
        // given
        Finished player = new Blackjack(hand(
            card(Rank.ACE, Suit.SPADE),
            card(Rank.KING, Suit.HEART)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.NINE, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(1500L);
    }

    @Test
    void 플레이어와_딜러_모두_블랙잭이면_무승부다() {
        // given
        Finished player = new Blackjack(hand(
            card(Rank.ACE, Suit.SPADE),
            card(Rank.KING, Suit.HEART)
        ));
        Finished dealer = new Blackjack(hand(
            card(Rank.ACE, Suit.CLOVER),
            card(Rank.KING, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(0L);
    }

    @Test
    void 딜러가_블랙잭이면_배팅금액만큼_잃는다() {
        // given
        Finished player = new Stay(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.SEVEN, Suit.HEART)
        ));
        Finished dealer = new Blackjack(hand(
            card(Rank.ACE, Suit.CLOVER),
            card(Rank.KING, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 플레이어_점수가_더_크면_배팅금액만큼_얻는다() {
        // given
        Finished player = new Stay(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.NINE, Suit.HEART)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.SEVEN, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(1000L);
    }

    @Test
    void 딜러_점수가_더_크면_배팅금액만큼_잃는다() {
        // given
        Finished player = new Stay(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.SEVEN, Suit.HEART)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.NINE, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 플레이어와_딜러_점수가_동일하면_수익이_0이다() {
        // given
        Finished player = new Stay(hand(
            card(Rank.TEN, Suit.SPADE),
            card(Rank.EIGHT, Suit.HEART)
        ));
        Finished dealer = new Stay(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.EIGHT, Suit.DIAMOND)
        ));
        // when
        long profit = player.profit(1000L, dealer);
        // then
        assertThat(profit).isEqualTo(0L);
    }

    private Hand hand(Card... cards) {
        return new Hand(List.of(cards));
    }

    private Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }
}