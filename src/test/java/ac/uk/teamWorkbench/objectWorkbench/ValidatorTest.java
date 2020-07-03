package ac.uk.teamWorkbench.objectWorkbench;

import ac.uk.teamWorkbench.objectWorkbench.objectDisplay.Validator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testIsValidAddRequest() {
        //Initialise an empty ArrayList for testing
        Validator validator = new Validator(new ArrayList<>());

        //If the string equals '+' it should return true
        assertTrue("Should return true", validator.isValidAddRequest("+"));
        //If string is not equal to '+' it should return false
        assertFalse("Should return false", validator.isValidAddRequest("Object"));
    }

    @Test
    public void testIsProtectedCharacter() {
        //Initialise an ArrayList containing protected characters
        ArrayList<String> protectedChars = new ArrayList<>();
        protectedChars.add("A");
        protectedChars.add("*");
        protectedChars.add("!");

        //Initialise the validator object using the protected character array
        Validator validator = new Validator(protectedChars);

        assertTrue("Should return true if character is protected",
                validator.isProtectedCharacter("*"));

        assertFalse("Should return false if character is not protected",
        validator.isProtectedCharacter("B"));
    }

    @Test
    public void testHasProtectedCharacter() {
        //Initialise an ArrayList containing protected characters
        ArrayList<String> protectedChars = new ArrayList<>();
        protectedChars.add("A");
        protectedChars.add("*");
        protectedChars.add("!");

        //Initialise the validator object using the protected character array
        Validator validator = new Validator(protectedChars);

        assertTrue("Should return true if protected is contained in string",
                validator.hasProtectedCharacter("*Object*"));

        assertFalse("Should return false if no protected characters are in the string",
                validator.hasProtectedCharacter("Object"));
    }
}