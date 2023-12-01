import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {



        Map<String, String> replacements = new HashMap<>();
        replacements.put("one", "1");
        replacements.put("two", "2");
        replacements.put("three", "3");
        replacements.put("four", "4");
        replacements.put("five", "5");
        replacements.put("six", "6");
        replacements.put("seven", "7");
        replacements.put("eight", "8");
        replacements.put("nine", "9");

        String puzzle = readFromFile(Paths.get("src/puzzle2.txt"));
        String replacedPuzzleInput = replaceAll(puzzle, replacements);
    //    System.out.println(replacedPuzzleInput);
        System.out.println(getCalibrationValue(replacedPuzzleInput));
    }


    private static int getCalibrationValue(String puzzleInput) {
        String[] puzzlelines = puzzleInput.split("\\n");
        return Stream.of(puzzlelines).map(i -> i.split(""))
                .map(Arrays::asList)
                .map(list -> list.stream()
                        .filter(str -> str.matches("\\d"))
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .map(list -> Integer.parseInt(list.get(0) + list.get(list.size() - 1)))
                .reduce(0, Integer::sum);
    }

    private static String replaceAll(String originalPuzzle, Map<String, String> replacements) {
        String patternString = "(" + String.join(")|(",replacements.keySet()) + ")";
 //       String patternString = "(?:nine)|(?:six)|(?:four)|(?:one)|(?:seven)|(?:two)|(?:three)|(?:five)|(?:eight)";
        System.out.println(patternString);
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(originalPuzzle);

        while (matcher.find()) {
            String spelledOutDigit = matcher.group();
            String numericalDigit = replacements.get(spelledOutDigit);
            originalPuzzle = originalPuzzle.replaceFirst(spelledOutDigit, numericalDigit);
        }
        return originalPuzzle;
    }

    public static String readFromFile(Path path) throws IOException {
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }

}