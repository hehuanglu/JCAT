public class SplitStringIntoFourDistinctStrings {
    public static boolean isPossible(String s)
    {
        int n = s.length();

        // At least 4 characters are needed to form
        // four non-empty substrings.
        if (n < 4) {
            return false;
        }

        // Any string of length 10 or more can always be
        // split into four non-empty distinct substrings.
        if (n >= 10) {
            return true;
        }

        // Try all possible ways to split the string into 4
        // parts.
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {

                    String a = s.substring(0, i);
                    String b = s.substring(i, j);
                    String c = s.substring(j, k);
                    String d = s.substring(k);

                    if (!a.equals(b) && !a.equals(c)
                            && !a.equals(d) && !b.equals(c)
                            && !b.equals(d) && !c.equals(d)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
