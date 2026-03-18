package blackjack.domain.participants;

import blackjack.domain.participants.state.Hit;
import blackjack.domain.participants.state.State;
import blackjack.domain.participants.state.StateFactory;

public class Player extends Participant {
    private final Bet bet;

    private Player(Name name, State state, Bet bet) {
        super(name, state);
        this.bet = bet;
    }

    public Player(Name name, Hand hand, Bet bet) {
        this(name, StateFactory.from(hand), bet);
    }

    public static Player createEmptyHand(Name name, Bet bet) {
        return new Player(name, new Hit(Hand.empty()), bet);
    }

    public boolean canHit() {
        return !state.isFinished();
    }

    public long profitAgainst(Dealer dealer) {
        return state.profit(bet.amount(), dealer.finishedState());
    }
}
