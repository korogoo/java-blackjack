package blackjack.domain.participants.state;

import blackjack.domain.card.Card;
import blackjack.domain.game.Score;
import blackjack.domain.participants.Hand;
import java.util.List;

public abstract class Started implements State {
    protected final Hand hand;

    public Started(final Hand hand) {
        this.hand = hand;
    }

    @Override
    public Score score() {
        return hand.calculateScore();
    }

    @Override
    public List<Card> getCards() {
        return hand.getCards();
    }
}
