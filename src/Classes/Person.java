package Classes;

import java.time.LocalDate;

public class Person {
    private final String name;
    private final LocalDate birth;

    public Person(String name, LocalDate birth) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (birth == null || birth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be null or in the future");
        }

        this.name = name;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', birth=" + birth + "}";
    }
}