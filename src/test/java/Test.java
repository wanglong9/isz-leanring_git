import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssr on 2017/3/14.
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add(":");
        list.add("ds");

        String[] a = new String[list.size()];
                list.toArray(a);

        for (String i: a  ) {
            System.out.printf(i);
        }
    }

}
