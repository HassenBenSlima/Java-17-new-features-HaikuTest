package test;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;

class Averager implements IntConsumer {
    private int total = 0;
    private int count = 0;

    public double average() {
        return count > 0 ? ((double) total) / count : 0;
    }

    public  void accept(int i) {
        total += i;
        count++;
    }

    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }

    public String toString() {
        return String.format("Total Salaries = %d, Number Developers = %d, Average Salary = %.2f", this.total, this.count, average());
    }
}

class Developer {
    private String name;
    private static int salary;

    public Developer(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public static Integer getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

}

class TestCollectMethodLambda {

    public static void main(String[] args) {
        List developers = Arrays.asList(
                new Developer("Jack", 5400),
                new Developer("Joe", 7500),
                new Developer("Jane", 6500),
                new Developer("Mary", 6200));

//        Averager averageCollect = developers.stream()
//                .map(Developer::getSalary)
//                .collect(Averager::new, Averager::accept, Averager::combine);

      //  System.out.println(averageCollect);
// Total Salaries = 25600, Number Developers = 4, Average Salary = 6400,00
    }
}
		
