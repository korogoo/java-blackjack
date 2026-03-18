package blackjack.domain.participants;

import blackjack.domain.game.EarningRate;

public record Bet(long amount) {
    public Bet {
        validatePositive(amount);
    }

    private static void validatePositive(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("배팅 금액은 양수여야 합니다.");
        }
    }

    public long calculateProfit(EarningRate earningRate) {
        return earningRate.calculateProfit(amount);
    }
}
