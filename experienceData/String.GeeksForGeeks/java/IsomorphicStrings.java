public class IsomorphicStrings {
    public static boolean areIsomorphic1(String s1, String s2) {

        int n = s1.length();

        // Check every character of s1
        for (int i = 0; i < n; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            // Check all occurrences of c1 in s1
            // and corresponding occurrences of c2 in s2
            for (int j = 0; j < n; j++) {

                // If we find another occurrence of c1 in s1,
                // it must match the corresponding character in s2
                if (s1.charAt(j) == c1 && s2.charAt(j) != c2) {
                    return false;
                }

                // If we find another occurrence of c2 in s2,
                // it must match the corresponding character in s1
                if (s2.charAt(j) == c2 && s1.charAt(j) != c1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean areIsomorphic2(String s1, String s2) {
        int n = s1.length();

        // marked[v] is true if character
        // 'a'+v from s2 is already used
        boolean[] marked = new boolean[26];

        // map[u] stores the character index
        // in s2 that s1's 'a'+u maps to
        int[] map = new int[26];
        for (int i = 0; i < 26; i++) map[i] = -1;

        for (int i = 0; i < n; i++) {
            int u = s1.charAt(i) - 'a';
            int v = s2.charAt(i) - 'a';

            // If s1[i] has not been mapped yet
            if (map[u] == -1) {
                // If s2[i] is already used
                // by another character
                if (marked[v]) return false;

                // Assign mapping and mark s2[i] as used
                map[u] = v;
                marked[v] = true;
            }
            // If already mapped, check for consistency
            else if (map[u] != v) {
                return false;
            }
        }

        return true;
    }
}
