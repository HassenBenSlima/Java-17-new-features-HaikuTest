package test;

public class A_Strings {

    public static void main(String[] args) {
        String numbers = """
                "one"
                'two'
                    three
                      four
                five
                    """;

        System.out.println("numbers ="+numbers);
    }
}
