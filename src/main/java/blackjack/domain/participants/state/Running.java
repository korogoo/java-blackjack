package blackjack.domain.participants.state;

import blackjack.domain.participants.Hand;

public abstract class Running extends Started {
    public Running(final Hand hand) {
        super(hand);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public long profit(final long betAmount, final Finished dealerState) {
        throw new IllegalArgumentException();
    }
}
