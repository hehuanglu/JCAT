class AntontheBoundary {
    public int returnToBoundaryCount(int[] nums) {
        int currPosition = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            currPosition += nums[i];
            if (currPosition == 0) {
                count++;
            }
        }
        return count;
    }
}
