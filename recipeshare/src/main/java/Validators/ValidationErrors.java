package Validators;

import java.util.ArrayList;

public class ValidationErrors extends RuntimeException {

    private ArrayList<String> errors = new ArrayList<>();

    public int getNumErrors() {
        return errors.size();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ValidationErrors(ArrayList<String> errors) {
        super("Validation errors: " + String.join(", ", errors));
        this.errors.addAll(errors);
    }
    
}
