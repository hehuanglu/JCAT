public class MinimumSwapsForBracketBalancing {
    static int swapCount(String s) {
        int ans = 0;

        //To store count of '['
        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '[')
                count++;
            else
                count--;

            //When count becomes less than 0
            if (count < 0) {

                //Start searching for '[' from (i+1)th index
                int j = i + 1;
                while (j < n) {

                    //When jth index contains '['
                    if (s.charAt(j) == '[')
                        break;
                    j++;
                }

                //Increment answer
                ans += j - i;

                //Set Count to 1 again
                count = 1;

                //Bring character at jth position to ith position
                //and shift all character from i to j-1
                //towards right
                char ch = s.charAt(j);
                StringBuilder newString = new StringBuilder(s);
                for (int k = j; k > i; k--) {
                    newString.setCharAt(k, s.charAt(k - 1));
                }
                newString.setCharAt(i, ch);
                s = newString.toString();
            }
        }

        return ans;
    }
}
