package source.Parsing;

import lombok.Getter;
import picocli.CommandLine;

@Getter
@CommandLine.Command(name = "show-categories", description = "Show list of categories")
public class ShowCategoriesListCommand { }
