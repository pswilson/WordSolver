import java.util.List;
import java.util.ArrayList;

public class Permutations {

    public Permutations() {
    }

    public ArrayList<String> getPerms(String remainder) {
        int len = remainder.length();
        ArrayList<String> result = new ArrayList<String>();

        if (len == 0) {
            result.add("");
            return result;
        }

        for (int i = 0; i < len; i++) {
            String before = remainder.substring(0, i);
            String after = remainder.substring(i + 1, len);
            ArrayList<String> partials = getPerms(before + after);

            for (String s : partials) {
                result.add(remainder.charAt(i) + s);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Permutations permGen = new Permutations();
        if (args.length >= 1) {
            List<String> list = permGen.getPerms(args[0]);
            System.out.println("There are " + list.size() + " permutations of \"" + args[0] + "\":");
            for (String s : list) {
                System.out.println(s);
            }
        } else {
            System.out.println("Usage: Permutations <string>");
        }
    }
}
