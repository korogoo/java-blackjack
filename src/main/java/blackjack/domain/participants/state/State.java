package blackjack.domain.participants.state;

import blackjack.domain.card.Card;
import blackjack.domain.game.Score;
import java.util.List;

public interface State {
    List<Card> getCards();

    Score score();

    State draw(final Card card);

    State stay();

    boolean isFinished();

    long profit(final long betAmount, final Finished dealerState);
}
