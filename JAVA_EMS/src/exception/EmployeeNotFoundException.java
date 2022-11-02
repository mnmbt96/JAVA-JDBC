package exception;

public class EmployeeNotFoundException extends Exception{

  private int id;

  public EmployeeNotFoundException(int id){
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "EmployeeNotFoundException [id=" + id + "]";
  }
  
}
