package utility;

import java.util.Date;

public class DateConversion {
    public static long getSqlDate (Date dateObject){
        return dateObject.getTime();
    }
}
