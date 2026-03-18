package blackjack.domain.participants.state;

import blackjack.domain.participants.Hand;

public class StateFactory {
    public static State from(Hand hand) {
        if (hand.isBlackjack()) {
            return new Blackjack(hand);
        }
        if (hand.isBust()) {
            return new Bust(hand);
        }
        return new Hit(hand);
    }
}
