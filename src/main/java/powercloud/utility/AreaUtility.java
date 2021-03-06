package powercloud.utility;

import powercloud.model.Area;

public class AreaUtility {

    public static boolean isAreaValid(Area area){       //verify if all data was informed
        if(area == null ||
                area.getId() == null ||
                area.getName().equals("") ||
                area.getDescription().equals("") ||
                area.getLocation().equals("") ||
                area.getColor().equals(""))
            return false;

        return true;
    }

}
