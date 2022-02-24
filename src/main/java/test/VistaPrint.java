package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VistaPrint {

    public static void main(String[] args) {

        String value="Hassen";
        List<Character> chars = value.chars().mapToObj(c->(char) c).collect(
            Collectors.toList()
        );

        Map<Integer,Character> map=new HashMap();
        for (int i = 0; i < chars.size(); i++) {
            map.put(i,chars.get(i));
        }
        map.entrySet().stream().forEach(System.out::println);

    }


}


