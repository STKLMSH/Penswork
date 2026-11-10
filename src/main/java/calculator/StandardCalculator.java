package calculator;

import model.EmployHistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class StandardCalculator implements ICalculator {
    public static final int DEFAULT_MIN_YEARS = 20;

    private static final BigDecimal BASE_RATE = new BigDecimal("0.55");
    private static final BigDecimal RATE_PER_EXTRA_YEAR = new BigDecimal("0.01");
    private static final BigDecimal MAX_RATE = new BigDecimal("0.75");

    private final int minInsuranceYears;

    public StandardCalculator() {
        this(DEFAULT_MIN_YEARS);
    }

    public StandardCalculator(int minInsuranceYears) {
        if (minInsuranceYears < 0) {
            throw new IllegalArgumentException("Минимальный стаж не может быть отрицательным!");
        }
        this.minInsuranceYears = minInsuranceYears;
    }

    @Override
    public PensForecast calculate(EmployHistory history) {
        int years = history.getTotalYears();
        BigDecimal averageSalary = history.getAverageMonthlySalary();
        if (years < minInsuranceYears) {
            return new PensForecast(years, averageSalary, BigDecimal.ZERO, BigDecimal.ZERO, false);
        }
        BigDecimal rate = BASE_RATE
                .add(RATE_PER_EXTRA_YEAR.multiply(BigDecimal.valueOf(years - minInsuranceYears)))
                .min (MAX_RATE);
        BigDecimal amount = averageSalary.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return new PensForecast(years, averageSalary, rate, amount, true);
    }
}
