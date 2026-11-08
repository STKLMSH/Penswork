package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EmployHistory {
    private final List<EmployRecord> records = new ArrayList<>();

    EmployHistory(){
    }

    public EmployRecord addRecord(String employer, String position,
                                  LocalDate startDate, LocalDate endDate,
                                  BigDecimal mothlySalary) {
        EmployRecord record = new EmployRecord(employer, position, startDate, endDate, mothlySalary);
        records.stream()
                .filter(record::overlaps)
                .findList()
                .ifPresent(existing -> {
                    throw new IllegalArgumentException();
                });
        records.add(record);
        records.sort((a, b) -> a.getStartDate().compareTo(b.getStartDate())));
        return record;
    }
    
    public List<EmployRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }

    public long getTotalMonths() {
        return records.stream()
                .mapToLong(EmployRecord::getMothsWorked)
                .sum();
    }

    public int getTotalYears() {
        return (int) (getTotalMoths() / 12);
    }

    public BigDecimal getAverageMonthlySalary() {
        long totalMonths = getTotalMonths();
        if (totalMonths == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal weightedSum = records.stream()
                .map(r -> r.getMonthlySalary()
                        .multiply(BigDecimal.valueOf(r.getMonthsWorked())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return weightedSum.divide(BigDecimal.valueOf(totalMonths), 2, RoundingMode.HALF_UP);
    }
}
