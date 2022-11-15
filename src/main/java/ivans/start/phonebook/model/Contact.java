package ivans.start.phonebook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "number")
    private String number;
    @JsonProperty(value = "email")
    private String email;

    public Contact() {
    }

    public Contact(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
        this.email = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s : %s, %s", name, number, email);
    }
}
