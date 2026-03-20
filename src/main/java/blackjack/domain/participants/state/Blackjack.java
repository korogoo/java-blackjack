package blackjack.domain.participants.state;

import blackjack.domain.game.EarningRate;
import blackjack.domain.participants.Hand;

public class Blackjack extends Finished {
    public Blackjack(final Hand hand) {
        super(hand);
    }

    @Override
    protected EarningRate earningRateForPlayer(final Finished dealerState) {
        if (dealerState.isBlackjack()) {
            return EarningRate.PUSH;
        }
        return EarningRate.BLACKJACK;
    }

    @Override
    protected boolean isBlackjack() {
        return true;
    }

    @Override
    protected boolean isBust() {
        return false;
    }
}
