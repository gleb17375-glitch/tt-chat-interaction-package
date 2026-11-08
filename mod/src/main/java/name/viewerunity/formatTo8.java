package name.viewerunity;

public class formatTo8 {
    public static final String format(String input) {
    if (input == null) input = "";
    final int MAX = 5;
    final String ELLIPSIS = "...";

    String result = input.length() > MAX
            ? input.substring(0, MAX) + ELLIPSIS   // 5 + 3 = exactly 8
            : input;

    return String.format("%-8s", result);
    }
}
