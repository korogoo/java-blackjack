package blackjack.domain.participants.state;

import blackjack.domain.card.Card;
import blackjack.domain.participants.Hand;

public class Hit extends Running {
    public Hit(final Hand hand) {
        super(hand);
    }

    @Override
    public State draw(final Card card) {
        hand.addCard(card);
        if (hand.isBust()) {
            return new Bust(hand);
        }
        if (hand.isBlackjack()) {
            return new Blackjack(hand);
        }
        return new Hit(hand);
    }

    @Override
    public State stay() {
        return new Stay(hand);
    }
}
