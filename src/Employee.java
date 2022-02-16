import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Employee implements Serializable {
    String ID;
    String fullName;
    String email;
    int age;
    Double salary;


    public Employee() {
    }

    public Employee(String ID, String fullName, String email, int age, double salary) {
        this.ID = ID;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.salary = salary;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
