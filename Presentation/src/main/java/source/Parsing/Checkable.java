package source.Parsing;

import source.Parsing.ParsingResults.ParsingResult;

public interface Checkable<T extends Commandable> {
    ParsingResult checkCommand(T command);
}
