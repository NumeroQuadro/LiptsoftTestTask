package source.Parsing.ParsingResults;

import lombok.Getter;
import source.Parsing.Commandable;

public abstract class ParsingResult {
    private ParsingResult() {
    }
    @Getter
    public static class Success<T extends Commandable> extends ParsingResult {
        private T command;
        public Success(T command) {
            super();
            this.command = command;
        }
    }

    @Getter
    public static class Failure extends ParsingResult {
        private final String failureMessage;
        public Failure(String message) {
            super();
            this.failureMessage = message;
        }
    }
}
