package service;

import calculator.ICalculator;
import calculator.PensForecast;
import exception.ApplicationNotFoundException;
import exception.BusinessRuleException;
import model.Admin;
import model.Client;
import model.PensApp;
import notification.Notifier;
import repository.AppRep;

import java.util.List;

public final class PensAppService {
    private final AppRep repository;
    private final ICalculator calculator;
    private final Notifier notifier;

    public PensAppService(AppRep repository, ICalculator calculator, Notifier notifier) {
        this.repository = repository;
        this.calculator = calculator;
        this.notifier = notifier;
    }

    public PensForecast forecast(Client client) {
        return calculator.calculate(client.getEmployHistory());
    }

    public PensApp submit(Client client) {
        if (client.getEmployHistory().isEmpty()) {
            throw new BusinessRuleException("Трудовая книга пуста");
        }
        boolean hasActive = repository.findByApplicant(client).stream()
                .anyMatch(a -> a.getStatus().isUnderReview());
        if (hasActive) {
            throw new BusinessRuleException("Уже рассматривается другое заявление");
        }
        PensApp app = repository.save(new PensApp(repository.nextId(), client));
        notifier.notify(client, "Заявление №" + app.getId() + " принято и поставлено в очередь");
        return app;
    }

    public PensApp startReview(long appId, Admin admin) {
        PensApp app = get(appId);
        app.startReview(admin);
        repository.save(app);
        notifier.notify(app.getApplicant(),
                "Заялвение №" + appId + " взято на проверку");
        return app;
    }

    public  PensApp approve(long appId) {
        PensApp app = get(appId);
        PensForecast forecast = forecast(app.getApplicant());
        if (!forecast.eligible())
        {
            throw new BusinessRuleException("Недостаточно стажа для назначения пенсии ("
                    + forecast.insuranceYears() + " лет");
        }
        app.approve(forecast.monthlyAmount());
        repository.save(app);
        notifier.notify(app.getApplicant(), "Заявление №" + appId
            + " одобрено, размер пенсии: " + forecast.monthlyAmount().toPlainString() + " BYN");
        return app;
    }

    public PensApp reject(long appId, String reason) {
        PensApp app = get(appId);
        app.reject(reason);
        repository.save(app);
        notifier.notify(app.getApplicant(),
            "Заявление №" + appId + " отклоненно: " + reason.trim());
        return app;
    }

    public PensApp close(long appId) {
        PensApp app = get(appId);
        app.close();
        repository.save(app);
        notifier.notify(app.getApplicant(),
                "Заявление №" + appId + " закрыто");
        return app;
    }

    public List<PensApp> findByApplicant (Client client) {
        return repository.findByApplicant(client);
    }

    public List<PensApp> findAll () {
        return repository.findAll();
    }

    public PensApp get(long appId) {
        return repository.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException(appId));
    }
}
