package calculator;

public final class StandardCalculator implements ICalculator {
    @Override
    public double calculate(int totalYears, double averageSalary) {
        return (totalYears) + (averageSalary);
    }
}
