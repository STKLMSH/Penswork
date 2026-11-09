package model;

import exception.InvalidStatusTransitionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class PensApp {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final long id;
    private final Client applicant;
    private final LocalDateTime createdAt;
    private AppStatus status = AppStatus.QUEUED;
    private Admin reviewer;
    private BigDecimal assignedAmount;
    private String rejectionReason;

    public PensApp(long id, Client applicant) {
        if (applicant == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.applicant = applicant;
        this.createdAt = LocalDateTime.now();
    }

    public void startReview(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException();
        }
        changeStatus(AppStatus.CHECK);
        this.reviewer = admin;
    }

    public void approve(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException();
        }
        changeStatus(AppStatus.APPROVED);
        this.assignedAmount = amount;
    }

    public void reject(String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException();
        }
        changeStatus(AppStatus.REJECTED);
        this.rejectionReason = reason.trim();
    }

    public void close() {
        changeStatus(AppStatus.CLOSED);
    }

    private void changeStatus(AppStatus target) {
        if (!status.canTransTo(target)) {
            throw new IllegalArgumentException();
        }
        status = target;
    }

    public long getId() {
        return id;
    }

    public Client getApplicant() {
        return applicant;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AppStatus getStatus() {
        return status;
    }

    public Optional<Admin> getReviewer() {
        return Optional.ofNullable(reviewer);
    }

    public Optional<BigDecimal> getAssignedAmount() {
        return Optional.ofNullable(assignedAmount);
    }

    public Optional<String> getRejectionReason() {
        return Optional.ofNullable(rejectionReason);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Заявление №").append(id)
                .append(" от ").append(createdAt.format(DATE_TIME_FORMATTER))
                .append(" ").append(applicant.getFullName())
                .append(" ").append(status.getTitle());
        getReviewer().ifPresent(r -> sb.append(" рассматривает: ").append(r.getFullName()));
        getAssignedAmount().ifPresent(a -> sb.append(" пенсия: ").
                append(a.toPlainString()).append(a.toPlainString()).append(" BYN"));
        getRejectionReason().ifPresent(r -> sb.append(" причина отказа: ").append(r));
        return sb.toString();
    }
}

