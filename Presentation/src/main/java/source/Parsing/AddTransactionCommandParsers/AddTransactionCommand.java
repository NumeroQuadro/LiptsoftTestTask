package source.Parsing.AddTransactionCommandParsers;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import source.Parsing.Commandable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@CommandLine.Command(name = "add-transaction", description = "Add transaction to a database")
public class AddTransactionCommand implements Commandable {
    @CommandLine.Option(names = {"-a", "--amount"}, description = "Amount of transaction", required = true)
    private BigDecimal amount;
    @CommandLine.Option(names = {"-d", "--date"}, description = "Date of transaction", required = true)
    private LocalDate date;
    @CommandLine.Option(names = {"-m", "--mcc"}, description = "MCC of transaction", required = false)
    private String mcc;
}
