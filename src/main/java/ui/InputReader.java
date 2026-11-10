package ui;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public final class InputReader {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    private final Scanner scanner;
    private final PrintStream out;

    public InputReader(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public String readLine(String prompt) {
        out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readNonBlank(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            out.println("Значение не может быть пустым");
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            String value = readLine(prompt);
            try {
                int number = Integer.parseInt(value);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }
            out.printf("Введите целое число от %d до %d. %n", min, max);
        }
    }

    public long readId(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                long id = Long.parseLong(value);
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException ignored) {
            }
            out.println("Введите положительное целое число");
        }
    }

    public BigDecimal readPositiveMoney(String prompt) {
        while (true) {
            String value = readLine(prompt).replace(',', '.');
            try {
                BigDecimal amount = new BigDecimal(value);
                if (amount.signum() > 0 && amount.scale() <= 2) {
                    return amount;
                }
            } catch (NumberFormatException ignored) {
            }
            out.println("Введите положительную сумму");
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            LocalDate date = parseDate(readLine(prompt));
            if (date != null) {
                return date;
            }
            out.println("Введите дату в формате ДД.ММ.ГГГГ");
        }
    }

    public LocalDate readOptionalDate(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (value.isEmpty()) {
                return null;
            }
            LocalDate date = parseDate(value);
            if (date != null) {
                return date;
            }
            out.println("Введите дату в формате ДД.ММ.ГГГГ или оставьте поле пустым");
        }
    }

    public boolean confirm(String prompt) {
        while (true) {
            String value = readLine(prompt + " (д/н): ").toLowerCase();
            switch (value) {
                case "д", "да", "y", "yes" -> {
                    return true;
                }
                case "н", "нет", "n", "no" -> {
                    return false;
                }
                default -> out.println("Да или Нет?");
            }
        }
    }

    private static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}