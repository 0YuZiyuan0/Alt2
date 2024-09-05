import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AltCalc {
    public final static int MAX_INPUT_LENGTH = 20;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение");
        String input = scanner.nextLine();
        if (input.length() > MAX_INPUT_LENGTH) {
            throw new RuntimeException("Максимальная длина строки должна быть не больше 10 символов");
        }
        String result = calculate(input);
        System.out.println("Результат: " + result);

    }

    public static String calculate(String input) {

        List<String> strings = parseString(input);
        if (strings.size() != 3) {
            throw new RuntimeException("Неверный формат ввода");
        }
        if (!validateQuotes(strings.get(0))) {
            throw new RuntimeException("Первым аргументом выражения должна быть строка, заключенная в кавычки.");
        }
        String result;
        switch (strings.get(1)) {
            case "+":
                result = calculatePlus(strings.get(0), strings.get(2));
                break;
            case "-":
                result = calculateMinus(strings.get(0), strings.get(2));
                break;
            case "*":
                result = calculateMultiply(strings.get(0), strings.get(2));
                break;
            case "/":
                result = calculateDivide(strings.get(0), strings.get(2));
                break;
            default:
                throw new RuntimeException("Нет такой операции");
        }
        if (result.length() > 40) {
            result = result.substring(0, 39) + "...";
        }
        return "\"" + result + "\"";
    }


    private static String calculateDivide(String first, String second) {
        if (!validateDigit(second)) {
            throw new RuntimeException("Второй аргумент должен быть целым числом от 1 до 10.");
        }
        int newLength = first.length() / Integer.parseInt(second);
        return removeQuotes(first).substring(0, newLength);
    }

    private static String calculateMultiply(String first, String second) {
        if (!validateDigit(second)) {
            throw new RuntimeException("Второй аргумент должен быть целым числом от 1 до 10.");
        }
        String s = removeQuotes(first);
        return s.repeat(Integer.parseInt(second));
    }


    private static String calculateMinus(String first, String second) {

        if (!validateQuotes(second)) {
            throw new RuntimeException("Строка должна быть заключена в кавычки");
        }
        first = removeQuotes(first);
        second = removeQuotes(second);
        return removeQuotes(first.replace(second, ""));
    }

    private static String calculatePlus(String first, String second) {

        if (!validateQuotes(second)) {
            throw new RuntimeException("Строка должна быть заключена в кавычки"
            );
        }
        return removeQuotes(first) + removeQuotes(second);
    }

    private static boolean validateQuotes(String s) {
        return s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"';
    }

    private static boolean validateDigit(String s) {
        try {
            int number = Integer.parseInt(s);
            return number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String removeQuotes(String s) {
        return s.replaceAll("\"", "");
    }

    public static List<String> parseString(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : input.toCharArray()) {
            if (c == '"') {
                insideQuotes = !insideQuotes;
                current.append(c);
            } else if (c == ' ' && !insideQuotes) {
                if (current.length() > 0) {
                    result.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            result.add(current.toString());
        }

        return result;
    }
}
