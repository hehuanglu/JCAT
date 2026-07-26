public class ShortestPath2PrintAStringOnScreen {
    public static int FindPath(String s) {

        // Initial position on the grid
        int curX = 0, curY = 0;

        // To store total steps required
        int res = 0;

        // Traverse each character in the string
        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Convert character to grid position
            int nextX = (ch - 'a') / 5;
            int nextY = (ch - 'a') % 5;

            // Move UP
            while (curX > nextX) {
                res++;
                curX--;
            }

            // Move LEFT
            while (curY > nextY) {
                res++;
                curY--;
            }

            // Move DOWN
            while (curX < nextX) {
                res++;
                curX++;
            }

            // Move RIGHT
            while (curY < nextY) {
                res++;
                curY++;
            }

            // Press the character
            res++;
        }

        return res;
    }
}
