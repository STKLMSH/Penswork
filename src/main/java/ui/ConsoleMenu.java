package ui;

import calculator.PensForecast;
import model.*;
import service.PensAppService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public final class ConsoleMenu {
    private final PensAppService service;
    private final Client client;
    private final Admin admin;
    private final InputReader input;
    private final PrintStream out;

    public ConsoleMenu(PensAppService service, Client client, Admin admin,
                       InputReader input, PrintStream out) {
        this.service = service;
        this.client = client;
        this.admin = admin;
        this.input = input;
        this.out = out;
    }

    public void run() {
        out.println(" Добро пожаловать! ");
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = input.readInt("Выберите пункт: ", 0, 10);
                running = handle(choice);
            }
        } catch (NoSuchElementException e) {
            out.println();
            out.println("Ввод завершен");
        }
        out.println("Работа завершена");
    }

    private void printMenu() {
        out.println();
        out.println(" Пользователь: " + client.getFullName());
        out.println("1 - Просмотр трудовой книги");
        out.println("2 - Добавить запись в трудовую книгу");
        out.println("3 - Расчёт пенсии");
        out.println("4 - Подать заявление на пенсию");
        out.println("5 - Мои заявления");
        out.println(" Администратор: " + admin.getFullName());
        out.println("6 - Все заявдения");
        out.println("7 - Открыть заявление");
        out.println("8 - Одобрить");
        out.println("9 - Отклонить");
        out.println("10 - Закрыть заявление");
        out.println("0 - Выход");
    }

    private boolean handle(int choice) {
        try {
            switch (choice) {
                case 1 -> showEmploymentHistory();
                case 2 -> addEmployRecord();
                case 3 -> showForecast();
                case 4 -> out.println("Подано: " + service.submit(client));
                case 5 -> printApp(service.findByApplicant(client));
                case 6 -> printApp(service.findAll());
                case 7 -> out.println("Обновлено: " + service.startReview(input.readId("Номер заявления: "), admin));
                case 8 -> out.println("Обновлено: " + service.approve(input.readId("Номер заявления: ")));
                case 9 -> {
                    long id = input.readId("Номер заявления: ");
                    String reason = input.readNonBlank("Причина отказа: ");
                    out.println("Обновлено: " + service.reject(id, reason));
                }
                case 10 -> out.println("Обновлено: " + service.close(input.readId("Номер заявления: ")));
                case 0 -> {
                    return !input.confirm("Завершить работу?");
                }
                default -> throw new IllegalStateException("Неизвестный пункт: " + choice);
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (RuntimeException e) {
            out.println("Ошибка: " + e.getMessage());
        }
        return true;
    }

    private void showEmploymentHistory() {
        List<EmployRecord> records = client.getEmployHistory().getRecords();
        if (records.isEmpty()) {
            out.println("Пусто");
            return;
        }
        out.println("Трудовая книга (" + client.getNumber() + "):");
        for (int i = 0; i < records.size(); i++) {
            out.printf("%2d. %s%n", i + 1, records.get(i));
        }
        out.println("Общий стаж: " + client.getEmployHistory().getTotalYears() + " полных лет");
    }

    private void addEmployRecord() {
        String employer = input.readNonBlank("Работадатель: ");
        String position = input.readNonBlank("Должность: ");
        LocalDate start = input.readDate("Дата приема: ");
        LocalDate end = input.readOptionalDate("Дата увольнения: ");
        BigDecimal salary = input.readPositiveMoney("Среднемесячный заработок: ");
        EmployRecord record = client.getEmployHistory().addRecord(employer, position, start, end, salary);
        out.println("Добавлено: " + record);
    }

    private void showForecast() {
        PensForecast forecast = service.forecast(client);
        out.println("Стаж: " + forecast.insuranceYears() + " полных лет");
        if (forecast.eligible()) {
            out.println("Коэффициент замещения: " + forecast.replacementRate().movePointRight(2).stripTrailingZeros().toPlainString());
            out.println("Прогноз пенсии: " + forecast.monthlyAmount().toPlainString() + "BYN/мес.");
        } else {
            out.println("Недостаточно стажа");
        }
    }

    private void printApp(List<PensApp> app) {
        if (app.isEmpty()) {
            out.println("Заявлений нет");
            return;
        }
        app.forEach(out::println);
    }
}
