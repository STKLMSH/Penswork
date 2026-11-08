package calculator;

import java.math.BigDecimal;

public record PensForecast (int insuranceYears,
                            BigDecimal averageSalary,
                            BigDecimal replacementRate,
                            BigDecimal monthlyAmount,
                            boolean eligible) {
}
