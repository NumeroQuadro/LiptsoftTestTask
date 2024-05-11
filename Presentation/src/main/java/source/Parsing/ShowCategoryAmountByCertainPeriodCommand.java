package source.Parsing;

import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "Show category amount by provided period of time")
public class ShowCategoryAmountByCertainPeriodCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-p", "--period"}, description = "Period to show stats (months, years)", defaultValue = "months")
    private String period;

    @Override
    public void run() {
        System.out.println("Category name: " + categoryName);
        System.out.println("Period: " + period);
    }
}
