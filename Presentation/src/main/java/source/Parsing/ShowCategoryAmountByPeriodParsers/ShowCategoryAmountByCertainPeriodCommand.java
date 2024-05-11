package source.Parsing.ShowCategoryAmountByPeriodParsers;

import lombok.Getter;
import picocli.CommandLine;
import source.Parsing.Commandable;

@Getter
@CommandLine.Command(name = "show", description = "Show category amount by provided period of time")
public class ShowCategoryAmountByCertainPeriodCommand implements Commandable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-p", "--period"}, description = "Period to show stats (months, years)", defaultValue = "months")
    private String period;
}
