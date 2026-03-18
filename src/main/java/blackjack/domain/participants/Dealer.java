package blackjack.domain.participants;

import blackjack.domain.card.Deck;
import blackjack.domain.participants.state.State;
import blackjack.domain.participants.state.StateFactory;

public class Dealer extends Participant {
    private static final Name DEALER_NAME = new Name("딜러");
    private static final int HIT_THRESHOLD = 16;

    private Dealer(State state) {
        super(DEALER_NAME, state);
    }

    public Dealer(Hand hand) {
        this(StateFactory.from(hand));
    }

    public static Dealer createEmptyHand() {
        return new Dealer(Hand.empty());
    }

    public static boolean isDealerName(Name name) {
        return DEALER_NAME.equals(name);
    }

    public int playTurn(Deck deck) {
        int hitCount = 0;
        while (getScore().isLessThanOrEqual(HIT_THRESHOLD)) {
            hitFrom(deck);
            hitCount++;
        }
        stay();
        return hitCount;
    }
}
