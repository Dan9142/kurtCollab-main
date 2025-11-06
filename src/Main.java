import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        String result = NumberFormat.getPercentInstance().format(0.01);
        System.out.println(result);
    }
}