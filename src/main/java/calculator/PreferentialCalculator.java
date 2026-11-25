package calculator;

import model.EmployHistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PreferentialCalculator implements  ICalculator {
    public static final int MIN_YEARS = 15;

    private static final BigDecimal BASE_RATE = new BigDecimal("0.55");
    private static final BigDecimal RATE_PER_EXTRA_YEAR = new BigDecimal("0.02");
    private static final BigDecimal MAX_RATE = new BigDecimal("0.75");

    @Override
    public PensForecast calculate(EmployHistory history) {
        int years = history.getTotalYears();
        BigDecimal averageSalary = history.getAverageMonthlySalary();
        if (years < MIN_YEARS) {
            return new PensForecast(years, averageSalary, BigDecimal.ZERO, BigDecimal.ZERO, false);
        }
            BigDecimal rate = BASE_RATE
                    .add(RATE_PER_EXTRA_YEAR.multiply(BigDecimal.valueOf(years - MIN_YEARS)))
                    .min(MAX_RATE);
            BigDecimal amount = averageSalary.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            return new PensForecast(years, averageSalary, rate, amount, true);
        }
    }
