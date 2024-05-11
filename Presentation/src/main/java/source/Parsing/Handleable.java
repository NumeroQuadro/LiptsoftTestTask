package source.Parsing;

import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;

public interface Handleable<T extends Commandable> {
    HandlingResult handleCommand(T command);
}
