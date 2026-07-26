public class String2MobileNumericKeypadSequence {
    static String printSequence(String arr[], String input)
    {
        String output = "";

        // length of input string
        int n = input.length();
        for (int i = 0; i < n; i++) {
            // Checking for space
            if (input.charAt(i) == ' ')
                output = output + "0";

            else {
                // Calculating index for each
                // character
                int position = input.charAt(i) - 'A';
                output = output + arr[position];
            }
        }

        // Output sequence
        return output;
    }
}
