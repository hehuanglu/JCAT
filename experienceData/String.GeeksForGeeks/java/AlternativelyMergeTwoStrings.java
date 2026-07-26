public class AlternativelyMergeTwoStrings {
    public static String merge(String s1, String s2) {
        // To store the final merged string
        String res = "";

        // Loop runs till both strings are fully traversed
        for (int i = 0; i < s1.length() || i < s2.length(); i++) {
            // If current index exists in first string
            if (i < s1.length())
                res += s1.charAt(i);

            // If current index exists in second string
            if (i < s2.length())
                res += s2.charAt(i);
        }
        return res;
    }
}
