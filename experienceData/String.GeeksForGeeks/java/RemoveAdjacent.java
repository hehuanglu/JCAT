public class RemoveAdjacent {
    static String rremove(String s)
    {
        // Create an empty string to build the result
        StringBuilder sb = new StringBuilder();
        // Get the size of the input string
        int n = s.length();

        // Iterate over the length of current string
        for (int i = 0; i < n; i++) {
            // Flag to check if the current
            // character is repeated
            boolean repeated = false;

            // Check if the current character is the same as
            // the next one
            while (i + 1 < n
                    && s.charAt(i) == s.charAt(i + 1)) {
                // Mark as repeated
                repeated = true;
                // Skip the next character since it's a
                // duplicate
                i++;
            }

            // If the character was not repeated,
            // add it to the result string
            if (!repeated)
                sb.append(s.charAt(i));
        }

        // If no changes were made, return the
        // result string
        if (n == sb.length())
            return sb.toString();

        // Otherwise, recursively call the function
        //  to check for more duplicates
        return rremove(sb.toString());
    }
}
