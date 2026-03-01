import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 1, 1, 8));

        int sum = numbers.stream().mapToInt(x -> x).sum();
        if (!numbers.contains(1) || sum > 11) {
            System.out.println(sum);
            return;
        }
        System.out.println(sum + 10);
    }
}
