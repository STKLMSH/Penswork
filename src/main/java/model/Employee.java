package model;

public final class Employee
{
    private final String companyName;
    private final int yearsOfExperience;
    private final double monthlySalary;

    public Employee(String companyName, int years, double salary)
    {
        this.companyName =companyName;
        this.yearsOfExperience =years;
        this.monthlySalary =salary;
    }

public String getCompanyName() {return companyName;}
public int getYearsOfExperience() {return yearsOfExperience;}
public double getmonthlySalary() {return monthlySalary;};
}
