package org.iesvdm.tienda;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbersList = Arrays.asList(1, 2, 3, 4, 5);

        IntStream intStream = numbersList.stream().mapToInt(Integer::intValue);

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // 1. forEach
        System.out.println("1. forEach:");
        IntStream.of(numbers).forEach(System.out::println);

        // 2. sum
        System.out.println("\n2. sum:");
        int sum = IntStream.of(numbers).sum();
        System.out.println("Sum: " + sum);

        // 3. average
        System.out.println("\n3. average:");
        OptionalDouble average = IntStream.of(numbers).average();
        System.out.println("Average: " + average.orElse(0.0));

        // 4. min
        System.out.println("\n4. min:");
        OptionalInt min = IntStream.of(numbers).min();
        System.out.println("Min: " + min.orElse(0));

        // 5. max
        System.out.println("\n5. max:");
        OptionalInt max = IntStream.of(numbers).max();
        System.out.println("Max: " + max.orElse(0));

        // 6. count
        System.out.println("\n6. count:");
        long count = IntStream.of(numbers).count();
        System.out.println("Count: " + count);

        // 7. summaryStatistics
        System.out.println("\n7. summaryStatistics:");
        IntSummaryStatistics stats = IntStream.of(numbers)
                .summaryStatistics();
        System.out.println("Stats: " + stats);

        // 8. filter and collect
        System.out.println("\n8. filter and collect:");
        int[] evenNumbers = IntStream.of(numbers)
                .filter(n -> n % 2 == 0)
                .toArray();
        System.out.print("Even Numbers: ");
        IntStream.of(evenNumbers).forEach(System.out::print);

        // 9. map and sum
        System.out.println("\n\n9. map and sum:");
        int squaredSum = IntStream.of(numbers)
                .map(n -> n * n)
                .sum();
        System.out.println("Sum of Squares: " + squaredSum);

    }
}
