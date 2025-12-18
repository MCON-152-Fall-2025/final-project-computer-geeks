package Validators;

public class ValidationErrors extends RuntimeException {

    public ValidationErrors(String message) {
        super(message);
    }

    public ValidationErrors() {
        super();
    }
    
}
