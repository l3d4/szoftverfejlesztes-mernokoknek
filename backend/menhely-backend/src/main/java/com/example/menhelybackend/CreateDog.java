package com.example.menhelybackend;

public class CreateDog {
    private String name;
    private String breed;

    public CreateDog() { }

    public CreateDog(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }

    public String getName() { return name; }
    public String getBreed() { return breed; }

    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }
}
