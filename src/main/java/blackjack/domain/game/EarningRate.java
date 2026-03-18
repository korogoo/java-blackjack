package blackjack.domain.game;

public enum EarningRate {
    BLACKJACK(3, 2),
    WIN(1, 1),
    LOSE(-1, 1),
    PUSH(0, 1);

    private final long numerator;
    private final long denominator;

    EarningRate(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public long calculateProfit(long betAmount) {
        return betAmount * this.numerator / this.denominator;
    }
}
