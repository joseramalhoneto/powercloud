package powercloud.utility;

import powercloud.model.SubArea;

public class SubAreaUtility {

    public static boolean isSubAreaValid(SubArea subArea){       //verify if all data was informed
        if(subArea == null)
            return false;

        if(subArea.getId() == null ||
                subArea.getId() < 1 ||
                subArea.getName().equals("") ||
                subArea.getEmployees() < 0)
            return false;

        return true;
    }

}
