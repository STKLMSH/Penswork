package calculator;

import model.PensionType;

public interface CalculatorFactory {
    ICalculator create(PensionType type);
}
