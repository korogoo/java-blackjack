package blackjack.domain.participants.state;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blackjack.domain.card.Card;
import blackjack.domain.card.Rank;
import blackjack.domain.card.Suit;
import blackjack.domain.game.EarningRate;
import blackjack.domain.participants.Hand;
import java.util.List;
import org.junit.jupiter.api.Test;

class FinishedTest {
    private final Hand lowerScoreHand =
        new Hand(List.of(new Card(Rank.TEN, Suit.DIAMOND)));
    private final Hand defaultScoreHand =
        new Hand(List.of(new Card(Rank.TEN, Suit.DIAMOND), new Card(Rank.FIVE, Suit.CLOVER)));
    private final Hand higherScoreHand =
        new Hand(List.of(new Card(Rank.TEN, Suit.DIAMOND), new Card(Rank.TEN, Suit.CLOVER)));
    private final Hand bustScoreHand =
        new Hand(List.of(new Card(Rank.TEN, Suit.DIAMOND), new Card(Rank.TEN, Suit.CLOVER),
            new Card(Rank.TWO, Suit.CLOVER)));
    private final Hand blackjackHand =
        new Hand(List.of(new Card(Rank.TEN, Suit.DIAMOND), new Card(Rank.ACE, Suit.CLOVER)));

    @Test
    void 둘_다_버스트가_아니면서_플레이어가_점수가_더_높다면_플레이어가_승리한다() {
        // given
        Finished playerState = new Stay(higherScoreHand);
        Finished dealerState = new Stay(lowerScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.WIN);
    }

    @Test
    void 둘_다_버스트가_아니면서_딜러가_점수가_더_높다면_딜러가_승리한다() {
        // given
        Finished playerState = new Stay(lowerScoreHand);
        Finished dealerState = new Stay(higherScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.LOSE);
    }

    @Test
    void 둘_다_버스트가_아니면서_점수가_같다면_무승부한다() {
        // given
        Finished playerState = new Stay(defaultScoreHand);
        Finished dealerState = new Stay(defaultScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.PUSH);
    }

    @Test
    void 플레이어가_버스트라면_딜러가_승리한다() {
        // given
        Finished playerState = new Bust(bustScoreHand);
        Finished dealerState = new Stay(defaultScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.LOSE);
    }

    @Test
    void 딜러만_버스트라면_플레이어가_승리한다() {
        // given
        Finished playerState = new Stay(defaultScoreHand);
        Finished dealerState = new Bust(bustScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.WIN);
    }

    @Test
    void 둘_다_버스트라면_딜러가_승리한다() {
        // given
        Finished playerState = new Bust(bustScoreHand);
        Finished dealerState = new Bust(bustScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.LOSE);
    }

    @Test
    void 플레이어만_블랙잭이면_플레이어가_블랙잭으로_승리한다() {
        // given
        Finished playerState = new Blackjack(blackjackHand);
        Finished dealerState = new Stay(defaultScoreHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.BLACKJACK);
    }

    @Test
    void 딜러만_블랙잭이면_딜러가_승리한다() {
        // given
        Finished playerState = new Stay(defaultScoreHand);
        Finished dealerState = new Blackjack(blackjackHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.LOSE);
    }

    @Test
    void 플레이어와_딜러_모두_블랙잭이면_무승부한다() {
        // given
        Finished playerState = new Blackjack(blackjackHand);
        Finished dealerState = new Blackjack(blackjackHand);
        // when
        EarningRate earningRate = playerState.earningRateForPlayer(dealerState);
        // then
        assertThat(earningRate).isEqualTo(EarningRate.PUSH);
    }
}
