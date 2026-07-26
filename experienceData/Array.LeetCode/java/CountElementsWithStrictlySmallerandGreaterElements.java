
class CountElementsWithStrictlySmallerandGreaterElements {
  public int countElements(int[] nums) {
    int minValue = nums[0];
    int maxValue = nums[0];
    for (int i = 0; i < nums.length; i++) {
      minValue = Math.min(minValue, nums[i]);
      maxValue = Math.max(maxValue, nums[i]);
    }
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
      int num = nums[i];
      if (num > minValue && num < maxValue) {
        count++;
      }
    }
    return count;
  }
}
