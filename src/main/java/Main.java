import model.*;
import notification.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.nio.charset.Charset;
import java.math.BigDecimal;

void main() {
    PensAppService service = new PensAppService(
            new InMemoryAppRep(),
            new StandartPensCalc(),
            new Notifier());

    Client client = new Client(1, "@sunboy", "Геннадий Герасимович", "301016A001PB1");
    client.getEmployHistory().addRecord("000 Отмыв", "Продавец-консультант",
            LocalDate.of(1999, 5, 24), LocalDate.of(2009, 6, 7), new BigDecimal("1000"));
    client.getEmployHistory().addRecord("000 Греб", "Старший продавец-консультант",
            LocalDate.of(2009, 9, 11), new BigDecimal("2000.00"));
    Admin admin = new Admin(100, "admin", "Владимир Владимирович");

    Charset consoleCharset = Charset.forName(
            System.getProperty("stdout.encoding", Charset.defaultCharset().name()));
    Scanner scanner = new Scanner(System.in, consoleCharset);
    new ConsoleMenu(service, client, admin, new InputReader(scanner, System.out), System.out).run();
}

