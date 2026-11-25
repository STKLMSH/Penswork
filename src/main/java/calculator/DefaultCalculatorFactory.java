package calculator;

import model.PensionType;

public final class DefaultCalculatorFactory implements  CalculatorFactory {

    @Override
    public ICalculator create(PensionType type) {
        if (type == null) {
            throw new IllegalStateException("Не указан вид пенсии!");
        }
        return switch (type) {
            case STANDARD -> new StandardCalculator();
            case PREFERENTIAL -> new PreferentialCalculator();
        };
    }
}
