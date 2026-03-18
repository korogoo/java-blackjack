package blackjack.domain.participants.state;

import blackjack.domain.card.Card;
import blackjack.domain.game.EarningRate;
import blackjack.domain.participants.Hand;

public abstract class Finished extends Started {
    public Finished(final Hand hand) {
        super(hand);
    }

    @Override
    public final State draw(final Card card) {
        throw new IllegalArgumentException();
    }

    @Override
    public final State stay() {
        return new Stay(hand);
    }

    @Override
    public final boolean isFinished() {
        return true;
    }

    @Override
    public final long profit(final long betAmount, final Finished dealerState) {
        EarningRate earningRate = earningRateForPlayer(dealerState);
        return earningRate.calculateProfit(betAmount);
    }

    protected abstract EarningRate earningRateForPlayer(Finished dealerState);
}
