package source.ResultTypes;

import lombok.Getter;
import lombok.Setter;

public abstract class OperationResult {
    private OperationResult() {
    }
    @Getter
    public static class Success extends OperationResult {
        private final String successMessage;
        public Success(String message) {
            super();
            this.successMessage = message;
        }
    }

    @Getter
    public static class Failure extends OperationResult {
        private final String failureMessage;
        public Failure(String message) {
            super();
            this.failureMessage = message;
        }
    }
}
