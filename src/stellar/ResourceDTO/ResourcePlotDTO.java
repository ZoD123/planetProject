package stellar.ResourceDTO;

import java.util.HashMap;

public class ResourcePlotDTO {
    public HashMap<String, SingleResourceDataDTO> plotData;

    public ResourcePlotDTO() {
        plotData = new HashMap<String, SingleResourceDataDTO>();
    }
}
