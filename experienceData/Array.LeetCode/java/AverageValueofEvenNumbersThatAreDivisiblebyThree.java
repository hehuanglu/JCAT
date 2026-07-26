
class AverageValueofEvenNumbersThatAreDivisiblebyThree {
  public int averageValue(int[] nums) {
    int total = 0;
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
      int num = nums[i];
      if (num % 2 == 0 && num % 3 == 0) {
        total += num;
        count++;
      }
    }
    return count == 0 ? 0 : total / count;
  }
}
