package entity.abstracts;

public abstract class Person {

    private final String firstname;
    private final String lastname;
    private final String nationalCode;

    public Person(String firstname, String lastname, String nationalCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationalCode = nationalCode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNationalCode() {
        return nationalCode;
    }
}
