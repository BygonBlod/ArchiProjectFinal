package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getStringDate(Date d){
        String res="";
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        res=simpleDateFormat.format(d);
        return res;
    }
}
