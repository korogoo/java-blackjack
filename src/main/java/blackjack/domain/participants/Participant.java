package blackjack.domain.participants;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.game.Score;
import blackjack.domain.participants.state.State;
import java.util.List;

abstract class Participant {
    protected final Name name;
    protected State state;

    public Participant(Name name, State state) {
        this.name = name;
        this.state = state;
    }

    public final String getName() {
        return name.getValue();
    }

    public final List<Card> getCards() {
        return state.getCards();
    }

    public final Score getScore() {
        return state.score();
    }

    public final void hitFrom(Deck deck) {
        state = state.draw(deck.draw());
    }

    public final void stay() {
        state = state.stay();
    }
}
