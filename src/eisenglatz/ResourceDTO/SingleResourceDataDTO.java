package eisenglatz.ResourceDTO;

import java.util.HashMap;

public class SingleResourceDataDTO {
    public String className;
    public HashMap<Integer, Integer>  measureMap;

    public SingleResourceDataDTO(){
        measureMap = new HashMap<Integer, Integer>();
    }
}
