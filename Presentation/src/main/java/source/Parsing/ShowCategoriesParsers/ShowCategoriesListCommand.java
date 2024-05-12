package source.Parsing.ShowCategoriesParsers;

import lombok.Getter;
import picocli.CommandLine;
import source.Parsing.Commandable;

@CommandLine.Command(name = "show-categories", description = "Show list of categories")
public class ShowCategoriesListCommand implements Commandable { }
