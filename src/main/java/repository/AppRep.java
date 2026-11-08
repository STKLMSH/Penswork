package repository;

import model.AppStatus;
import model.Client;
import model.PensApp;

import java.util.List;
import java.util.Optional;

public interface AppRep {

    long nextId();

    PensApp save(PensApp app);

    Optional<PensApp> findBYId(long id);

    List<PensApp> findAll();

    List<PensApp> findByApplicant(Client applicant);

    List<PensApp> findByStatus(AppStatus status);
}
