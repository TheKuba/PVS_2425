package streaming;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieLambda {
    public static void main(String[] args) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("data\\movieList.txt"));
            String[] params;
            for(String line : lines){
                params = line.split(";");
                movies.add(
                        new Movie(
                                params[0],
                                Integer.parseInt(params[1]),
                                params[2],
                                Double.parseDouble(params[3]))
                );
            }

            movies.stream()
                    .filter(movie -> movie.getYear() > 2000)
                    .forEach(System.out::println);

            System.out.println("=======");

            movies.stream()
                    .sorted(Comparator.comparingDouble(Movie::getRating))
                    .forEach(System.out::println);

            double avgRating = movies.stream()
                    .filter(movie -> movie.genre.equals("Horror"))
                    .distinct()
                    .mapToDouble(Movie::getRating)
                    .average()
                    .orElse(0);

            System.out.println( movies.stream()
                    .mapToDouble(Movie::getRating)
                    .summaryStatistics());

            List<Movie> actionMovies = movies.stream()
                    .filter(movie -> movie.genre.equals("Action") && movie.year > 2000)
                    .distinct()
                    .toList();
        } catch (IOException e) {
            System.out.println("Nepodarilo se nacist data:");
            System.out.println(e.getMessage());;
        }

    }
} class Movie{
    String name;
    int year;
    String genre;
    double rating;

    public Movie(String name, int year, String genre, double rating) {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " (" + year + ")\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
