public class KthNonRepeatingCharacter {
    public static char kthNonRepeatingChar(String str, int k) {
        // Initialize count and result variables to 0 and
        // null character, respectively
        int count = 0;
        char result = '\0';

        // Loop through each character in the string
        for (int i = 0; i < str.length(); i++) {
            // Assume that the current character does not
            // repeat
            boolean repeating = false;

            // Loop through the rest of the string to check
            // if the current character repeats
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    // If the current character repeats, set
                    // the repeating flag to true and exit
                    // the loop
                    repeating = true;
                    break;
                }
            }

            // If the current character does not repeat,
            // increment the count of non-repeating
            // characters
            if (!repeating) {
                count++;
                // If the count of non-repeating characters
                // equals k, set the result variable to the
                // current character and exit the loop
                if (count == k) {
                    result = str.charAt(i);
                    break;
                }
            }
        }

        // Return the result variable
        return result;
    }
}
