package blackjack.domain.participants.state;

import blackjack.domain.game.EarningRate;
import blackjack.domain.participants.Hand;

public class Bust extends Finished {
    public Bust(final Hand hand) {
        super(hand);
    }

    @Override
    protected EarningRate earningRateForPlayer(final Finished dealerState) {
        return EarningRate.LOSE;
    }
}
