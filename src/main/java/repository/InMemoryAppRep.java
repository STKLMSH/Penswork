package repository;

import model.AppStatus;
import model.Client;
import model.PensApp;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public final class InMemoryAppRep implements AppRep {
    private final Map<Long, PensApp> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public long nextId() {
        return sequence.incrementAndGet();
    }

    @Override
    public PensApp save(PensApp app) {
        storage.put(app.getId(), app);
        return app;
    }

    @Override
    public Optional<PensApp> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<PensApp> findAll() {
        return filter(a -> true);
    }

    @Override
    public List<PensApp> findByApplicant(Client applicant) {
        return filter(a -> a.getApplicant().equals(applicant));
    }

    @Override
    public List<PensApp> findByStatus(AppStatus status) {
        return filter(a -> a.getStatus() == status);
    }

    private List<PensApp> filter(Predicate<PensApp> condition) {
        return storage.values().stream()
                .filter(condition)
                .sorted(Comparator.comparingLong(PensApp::getId))
                .toList();
    }
}
