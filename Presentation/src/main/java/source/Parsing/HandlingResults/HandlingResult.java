package source.Parsing.HandlingResults;

import lombok.Getter;
import source.Parsing.Commandable;
import source.Parsing.ParsingResults.ParsingResult;

public class HandlingResult {
    private HandlingResult() {
    }
    @Getter
    public static class Success extends HandlingResult {
        private final String successMessage;
        public Success(String message) {
            super();
            this.successMessage = message;
        }
    }

    @Getter
    public static class Failure extends HandlingResult {
        private final String failureMessage;
        public Failure(String message) {
            super();
            this.failureMessage = message;
        }
    }
}
