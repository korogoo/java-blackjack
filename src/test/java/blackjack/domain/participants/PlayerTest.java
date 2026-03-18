package blackjack.domain.participants;

import static org.assertj.core.api.Assertions.*;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Rank;
import blackjack.domain.card.ShuffledDeck;
import blackjack.domain.card.Suit;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlayerTest {
    @Test
    void 플레이어는_처음에_hit_할_수_있다() {
        // given
        Player player = Player.createEmptyHand(
            new Name("pobi"),
            new Bet(1000L));
        // when
        boolean result = player.canHit();
        // then
        assertThat(result).isTrue();
    }

    @Test
    void 플레이어가_stay_하면_더_이상_hit_할_수_없다() {
        // given
        Player player = Player.createEmptyHand(
            new Name("pobi"),
            new Bet(1000L));
        // when
        player.stay();
        // then
        assertThat(player.canHit()).isFalse();
    }

    @Test
    void 플레이어가_버스트하면_더_이상_hit_할_수_없다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.KING, Suit.SPADE),
                card(Rank.QUEEN, Suit.HEART)
            ),
            new Bet(1000L)
        );

        // when
        Deck deck = new ShuffledDeck(List.of(new Card(Rank.TWO, Suit.DIAMOND)));
        player.hitFrom(deck);
        // then
        assertThat(player.canHit()).isFalse();
    }

    @Test
    void 플레이어가_버스트하면_배팅금액만큼_잃는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.KING, Suit.SPADE),
                card(Rank.QUEEN, Suit.HEART),
                card(Rank.TWO, Suit.DIAMOND)
            ),
            new Bet(1000L)
        );
        Dealer dealer = new Dealer(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.SEVEN, Suit.HEART)
        ));
        dealer.stay();
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 딜러가_버스트하면_플레이어는_배팅금액만큼_얻는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.TEN, Suit.SPADE),
                card(Rank.SEVEN, Suit.HEART)
            ),
            new Bet(1000L)
        );
        player.stay();

        Dealer dealer = new Dealer(hand(
            card(Rank.KING, Suit.CLOVER),
            card(Rank.QUEEN, Suit.DIAMOND),
            card(Rank.TWO, Suit.HEART)
        ));
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(1000L);
    }

    @Test
    void 딜러가_블랙잭이면_플레이어는_배팅금액만큼_잃는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.TEN, Suit.SPADE),
                card(Rank.SEVEN, Suit.HEART)
            ),
            new Bet(1000L)
        );
        player.stay();

        Dealer dealer = new Dealer(hand(
            card(Rank.ACE, Suit.CLOVER),
            card(Rank.KING, Suit.DIAMOND)
        ));
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 플레이어_점수가_더_크면_배팅금액만큼_얻는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.TEN, Suit.SPADE),
                card(Rank.NINE, Suit.HEART)
            ),
            new Bet(1000L)
        );
        player.stay();

        Dealer dealer = new Dealer(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.SEVEN, Suit.DIAMOND)
        ));
        dealer.stay();
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(1000L);
    }

    @Test
    void 딜러_점수가_더_크면_배팅금액만큼_잃는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.TEN, Suit.SPADE),
                card(Rank.SEVEN, Suit.HEART)
            ),
            new Bet(1000L)
        );
        player.stay();

        Dealer dealer = new Dealer(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.NINE, Suit.DIAMOND)
        ));
        dealer.stay();
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(-1000L);
    }

    @Test
    void 플레이어와_딜러_점수가_같으면_수익은_0이다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.TEN, Suit.SPADE),
                card(Rank.EIGHT, Suit.HEART)
            ),
            new Bet(1000L)
        );
        player.stay();

        Dealer dealer = new Dealer(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.EIGHT, Suit.DIAMOND)
        ));
        dealer.stay();
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(0L);
    }

    @Test
    void 플레이어가_블랙잭이면_배팅금액의_1_5배를_얻는다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.ACE, Suit.SPADE),
                card(Rank.KING, Suit.HEART)
            ),
            new Bet(1000L)
        );
        Dealer dealer = new Dealer(hand(
            card(Rank.TEN, Suit.CLOVER),
            card(Rank.NINE, Suit.DIAMOND)
        ));
        dealer.stay();
        // when
        long profit = player.profitAgainst(dealer);
        // then
        assertThat(profit).isEqualTo(1500L);
    }

    @Test
    void 플레이어와_딜러가_모두_블랙잭이면_수익은_0이다() {
        // given
        Player player = new Player(
            new Name("pobi"),
            hand(
                card(Rank.ACE, Suit.SPADE),
                card(Rank.KING, Suit.HEART)
            ),
            new Bet(1000L)
        );
        Dealer dealer = new Dealer(hand(
            card(Rank.ACE, Suit.CLOVER),
            card(Rank.KING, Suit.DIAMOND)
        ));
        // when
        long profit = player.profitAgainst(dealer);
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