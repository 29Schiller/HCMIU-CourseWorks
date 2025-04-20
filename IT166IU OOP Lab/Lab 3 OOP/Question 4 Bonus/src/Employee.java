public class Employee implements Comparable<Employee> {
    private String employeeID;
    private String employeeName;
    private int salaryPerHour;
    private int noOfLeavingDay;
    private int noOfTravelDay;

    // Constructor of class
    public Employee(String employeeID, String employeeName, int salaryPerHour, int noOfLeavingDay, int noOfTravelDay) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.salaryPerHour = salaryPerHour;
        this.noOfLeavingDay = noOfLeavingDay;
        this.noOfTravelDay = noOfTravelDay;
    }


    // Create Getter and Setter for class
    public String getEmployeeID() {
        return employeeID;
    }


    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }


    public String getEmployeeName() {
        return employeeName;
    }


    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    public int getSalaryPerHour() {
        return salaryPerHour;
    }


    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }


    public int getNoOfLeavingDay() {
        return noOfLeavingDay;
    }


    public void setNoOfLeavingDay(int noOfLeavingDay) {
        this.noOfLeavingDay = noOfLeavingDay;
    }


    public int getNoOfTravelDay() {
        return noOfTravelDay;
    }


    public void setNoOfTravelDay(int noOfTravelDay) {
        this.noOfTravelDay = noOfTravelDay;
    }
    // Create Getter and Setter for class
    
    // Method to calculate weekly salary
    public int calculateWeeklySalary() {
        int Weekly_Salary = salaryPerHour * 8 * (5 - noOfLeavingDay + noOfTravelDay/2);
        return Weekly_Salary;
    }

    @Override
    public int compareTo(Employee objectEmployee) {
        if (this.noOfTravelDay > objectEmployee.getNoOfTravelDay()){
            return 1;
        } else if (this.noOfTravelDay == objectEmployee.getNoOfTravelDay()){
            if (this.noOfLeavingDay > objectEmployee.getNoOfLeavingDay()){
                return -1;
            }
            else if (this.noOfLeavingDay == objectEmployee.getNoOfLeavingDay()){
                return 0;
            }
            else{
                return 1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "[Name:"+ employeeName +" - Salary Per Hour:" + salaryPerHour+ " - Weekly Salary:" + calculateWeeklySalary() + "]";
    }
}