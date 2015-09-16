package util;

import static util.Strings.requireString;

import org.junit.Test;

public class StringsRequireTest {

    @Test(expected = IllegalArgumentException.class)
    public void requireThrowsIfNullArg() {
        requireString(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void requireThrowsIfEmptyArg() {
        requireString("");
    }
    
    @Test
    public void requireDoesntThrowIfArgHasChars() {
        requireString("1");
    }
    
}
