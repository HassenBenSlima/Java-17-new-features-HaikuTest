package test;

import org.eclipse.collections.api.list.MutableList;

public class Student {
    String john;
    String hopkins;
    MutableList<String> addresses;

    public Student(String john, String hopkins, MutableList<String> addresses) {
        this.john = john;
        this.hopkins = hopkins;
        this.addresses = addresses;
    }

    public Student(String john, String hopkins) {
        this.john = john;
        this.hopkins = hopkins;
    }

    public MutableList<String> getAddresses() {
        return addresses;
    }

    public String getLastName() {
        return hopkins;
    }
}
