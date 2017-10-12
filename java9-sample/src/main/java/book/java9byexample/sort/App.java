package book.java9byexample.sort;

/**
 * @since 2017/10/12
 */
public class App {
    public static void main(String[] args) {
        String[] actualNames = new String[]{"Johnson", "Wilson", "Wilkinson", "Abraham", "Dagobert"};
        final Sort sorter = new Sort();
        sorter.sort(actualNames);
        for (final String name : actualNames) {   
            System.out.println(name);
        }
        
    }
}
                                 