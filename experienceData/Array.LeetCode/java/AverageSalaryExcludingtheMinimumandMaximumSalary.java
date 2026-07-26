
class AverageSalaryExcludingtheMinimumandMaximumSalary {
    public double average(int[] salary) {
        int minSalary = salary[0];
        int maxSalary = salary[0];
        double totalSalary = 0;
        for (int i = 0; i < salary.length; i++) {
            minSalary = Math.min(minSalary, salary[i]);
            maxSalary = Math.max(maxSalary, salary[i]);
            totalSalary += salary[i];
        }
        return (totalSalary - minSalary - maxSalary) / (salary.length - 2);
    }
}
