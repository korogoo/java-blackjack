package blackjack.domain.participants.state;

import blackjack.domain.game.EarningRate;
import blackjack.domain.game.Score;
import blackjack.domain.participants.Hand;

public class Stay extends Finished {
    public Stay(Hand hand) {
        super(hand);
    }

    @Override
    protected EarningRate earningRateForPlayer(final Finished dealerState) {
        if (dealerState.isBlackjack()) {
            return EarningRate.LOSE;
        }
        if (dealerState.isBust()) {
            return EarningRate.WIN;
        }
        return competeScoreWith(dealerState);
    }

    private EarningRate competeScoreWith(Finished dealerState) {
        Score dealerScore = dealerState.hand.calculateScore();
        Score playerScore = hand.calculateScore();

        if (playerScore.isBiggerThan(dealerScore)) {
            return EarningRate.WIN;
        }
        if (dealerScore.isBiggerThan(playerScore)) {
            return EarningRate.LOSE;
        }
        return EarningRate.PUSH;
    }

    @Override
    protected boolean isBlackjack() {
        return false;
    }

    @Override
    protected boolean isBust() {
        return false;
    }
}
