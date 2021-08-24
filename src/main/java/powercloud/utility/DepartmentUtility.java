package powercloud.utility;

import powercloud.model.Department;

public class DepartmentUtility {

    public static boolean isDepartmentValid(Department department){       //verify if all data was informed
        if(department == null ||
                department.getId() == null ||
                department.getName().equals("") ||
                department.getEmployees() < 0)
            return false;

        return true;
    }

}
