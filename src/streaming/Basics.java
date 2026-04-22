package streaming;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class Basics {
    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            numbers.add((int)(Math.random() * 100));
        }
        System.out.println(numbers);

        System.out.println("Size: " + numbers.size());
        int size = (int) numbers.stream()
                .count();

        int uniques = (int) numbers.stream()
                .distinct() //COUNT DISTINCT
                .count();

        System.out.println("Unikatnich: " + uniques);

        int uniquesUpper = (int) numbers.stream()
                .distinct()
                .filter(num -> num > 50)
                .count();

        numbers.stream()
                .filter(number -> number % 2 == 0)
                .forEach(System.out::println);

        numbers.stream()
                .sorted(Integer::compare)
                .forEach(System.out::println);

        String[] names = {"Ludmila", "Anastasia", "Jarmil", "Řehoř", "Jonáš"};
        Stream.of(names)
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);

        double average = numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);

        System.out.println("Average: " + average);
    }

}