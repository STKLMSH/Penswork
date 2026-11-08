package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class EmployRecord {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final String employer;
    private final String position;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal monthlySalary;

    EmployRecord(String employer, String position,
                     LocalDate startDate, LocalDate endDate,
                     BigDecimal monthlySalary) {
        if (employer == null || employer.isBlank()) {
            throw new IllegalArgumentException();
        }
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException();
        }
        if (startDate == null) {
            throw new IllegalArgumentException();
        }
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
        if (endDate != null && !endDate.isAfter(startDate)) {
            throw new IllegalArgumentException();
        }
        if (monthlySalary == null || monthlySalary.signum() <= 0) {
            throw new IllegalArgumentException();
        }
        this.employer = employer.trim();
        this.position = position.trim();
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlySalary = monthlySalary.setScale(2, RoundingMode.HALF_UP);
    }

    public long getMonthsWorked() {
        return ChronoUnit.MONTHS.between(startDate, effectiveEndDate());
    }

    boolean overlaps(EmployRecord other) {
        return !startDate.isAfter(other.effectiveEndDate()) && !other.startDate.isAfter(effectiveEndDate());
    }

    private LocalDate effectiveEndDate() {
        return endDate != null ? endDate : LocalDate.now();
    }

    public String getEmployer() {
        return employer;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Optional<LocalDate> getEndDate() {
        return Optional.ofNullable(endDate);
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public  String toString() {
        String end = endDate != null ? endDate.format(DATE_FORMAT) : "по н.в.";
        return String.format("%s - %s, %s - %s, %s BYN/мес.",
                employer, position, startDate.format(DATE_FORMAT), end, monthlySalary.toPlainString());
    }
}
