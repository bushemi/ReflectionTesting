package dto;

import annotations.CustomDateFormat;
import annotations.JsonValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Human {
    private String firstName;
    private String lastName;
    @JsonValue(name = "fun")
    private String hobby;
    @CustomDateFormat(format = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String formatter="";
    public Human(String firstName, String lastName, String hobby, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobby = hobby;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public LocalDate getBirthDate() {

        return birthDate;
    }



    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Human{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", hobby='").append(hobby).append('\'');
        sb.append(", birthDate=");
        if(formatter.equals("")){sb.append(birthDate);}
        else{sb.append(getFormattedBirthDate());}
        sb.append('}');
        return sb.toString();
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public void setBirthDateFromString(String birthDate) {
        this.birthDate = (LocalDate.parse(birthDate, DateTimeFormatter.ofPattern(formatter)));
    }

    public String getFormattedBirthDate() {
        String result = this.getBirthDate().format(DateTimeFormatter.ofPattern(formatter));
        return result;
    }
}
