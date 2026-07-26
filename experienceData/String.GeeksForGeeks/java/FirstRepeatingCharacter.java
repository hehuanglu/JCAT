public class FirstRepeatingCharacter {

    // Function to find the first repeating character in the
    // string
    public static String firstRepChar(String s) {
        // Get the size of the input string
        int n = s.length();

        // Iterate through each character in the string
        for (int i = 0; i < n; i++) {

            // Check if the current character is a repeating
            // character
            for (int j = 0; j < i; j++) {
                if (s.charAt(i) == s.charAt(j)) {

                    // Create a string to hold the repeating
                    // character
                    return Character.toString(s.charAt(i));
                }
            }
        }

        // If no repeating character is found, return "-1"
        return "-1";
    }
}