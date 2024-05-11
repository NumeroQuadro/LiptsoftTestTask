package source.Parsing.ShowAllExpensesByPeriodOfTimeParsers;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import source.Parsing.Commandable;

@Getter
@Setter
@CommandLine.Command(name = "show-all", description = "Show category amount by period of time")
public class ShowAllExpensesByPeriodOfTimeCommand implements Commandable {
    @CommandLine.Option(names = {"-p", "--period"}, description = "Period to show stats (months, years)", defaultValue = "months")
    private String period;
}
