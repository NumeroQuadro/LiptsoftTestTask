package source.Parsing;

import source.Parsing.HandlingResults.HandlingResult;

public interface Handleable<T extends Commandable> {
    HandlingResult handleCommand(T command);
}
