public class FirstNonRepeatingCharacterOfGivenString {
    public static char nonRep(String s) {
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            boolean found = false;

            for (int j = 0; j < n; ++j) {
                if (i != j && s.charAt(i) == s.charAt(j)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return s.charAt(i);
        }

        return '$';
    }
}
