package powercloud.utility;

import powercloud.model.Department;

public class DepartmentUtility {

    public static boolean isSubAreaValid(Department department){       //verify if all data was informed
        if(department == null)
            return false;

        if(department.getId() == null ||
                department.getId() < 1 ||
                department.getName().equals("") ||
                department.getEmployees() < 0)
            return false;

        return true;
    }

}
