package oleg.work.domain;

public class Person {
    private int id;
    private String name;
    private Cat pet;

    @Override
    public String toString() {
        return "person: " + name + ", pet: " + pet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getPet() {
        return pet;
    }

    public void setPet(Cat pet) {
        this.pet = pet;
    }
}
