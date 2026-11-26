package calculator;

import model.PensionType;

public final class FixedCalculatorFactory implements CalculatorFactory {
    private final ICalculator calculator;

    public FixedCalculatorFactory(ICalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("Не указан калькулятор!");
        }
        this.calculator = calculator;
    }

    @Override
    public ICalculator create(PensionType type) {
        return calculator;
    }
}
