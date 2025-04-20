import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Project implements Comparable<Project> {
    private String projectID;
    private Date startDate;
    private Date endDate;
    private ArrayList <Employee> listOfEmployees;

    // Constructor
    public Project(String projectID, Date startDate, Date endDate) {
        this.projectID = projectID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listOfEmployees = new ArrayList<>();
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getendDate() {
        return endDate;
    }

    public void setendDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployee(ArrayList<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    public void addEmployee(Employee employee) {
        listOfEmployees.add(employee);
    }

    public double estimateBudget() {
        long timeDiff = endDate.getTime() - startDate.getTime();
        double hoursDiff = (double) timeDiff / (1000 * 60 * 60);
        double totalSalary = 0;

        for (Employee employee : listOfEmployees) {
            totalSalary += employee.getSalaryPerHour() * 24 * hoursDiff;
        }

        return totalSalary;
    }

    @Override
    public String toString() {
        Collections.sort(listOfEmployees);
        return "Project [listOfEmployee=" + listOfEmployees + "]";
    }

    @Override
    public int compareTo(Project o) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
    
}