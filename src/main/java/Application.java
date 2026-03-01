import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 4, 6, 10));

        boolean containsAce = numbers.remove(Integer.valueOf(1));
        if (!containsAce) {
            System.out.println(0);
            return;
        }

        int sum = numbers.stream().mapToInt(a -> a).sum();
        if (sum > 10) {
            System.out.println(1);
            return;
        }
        System.out.println(11);
    }
}
