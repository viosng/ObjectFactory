package db.map.reduce;

import java.math.BigDecimal;

/**
 * Created by StudentDB on 09.04.2015.
 */
public class Employee {
    private final String name, company;
    private final BigDecimal salary;

    public Employee(String name, String company, BigDecimal salary) {
        this.salary = salary;
        this.company = company;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
