package ac.uk.teamWorkbench.objectWorkbench.objectDisplay;

import java.util.ArrayList;

/**
 * Validator
 *
 * Handles validation logic for requests for the ObjectWorkbench.
 */
public class Validator {

    private ArrayList<String> protectedCharacters;

    public Validator(ArrayList<String> protectedCharacters) {
        this.protectedCharacters = protectedCharacters;
    }

    /**
     * Checks the addTab request is to a valid tab
     * @param tabTitle - title of tab
     * @return true if tabtitle is "+" else false
     */
    public boolean isValidAddRequest(String tabTitle) {
        return tabTitle.equals("+");
    }

    /**
     * Checks if a string is a protected character
     * @param x - the string to check
     * @return true if the character is protected, else false
     */
    public boolean isProtectedCharacter(String x) {
        return protectedCharacters.contains(x);
    }

    /**
     * Checks if a string contains a protected character
     * @param x - the character
     * @return true if the string contains a protected character, else false
     */
    public boolean hasProtectedCharacter(String x) {
        boolean result = false;
        for(String character : protectedCharacters) {
            if(x.contains(character)) {
                result = true;
            }
        }
        return result;
    }
}
