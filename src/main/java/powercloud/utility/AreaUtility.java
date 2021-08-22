package powercloud.utility;

import powercloud.model.Area;

public class AreaUtility {

    public static boolean isAreaValid(Area area){       //verify if all data was informed
        if(area == null)
            return false;

        if(area.getId() == null ||
                area.getId() < 1 ||
                area.getName().equals("") ||
                area.getDescription().equals("") ||
                area.getColor().equals(""))
            return false;

        return true;
    }

}
