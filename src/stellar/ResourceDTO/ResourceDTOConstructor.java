package stellar.ResourceDTO;

import stellar.planet.Planet;

import java.util.HashMap;
import java.util.Map;

public class ResourceDTOConstructor {

    /**
     * class creates Resource DTOs
     */
    public ResourceDTOConstructor() {
    }

    /**
     * creates a snapshot of all resources which the planet have at the state of calling the method
     *
     * @param plotDTO DTO which holds the snapshot information
     * @param planet  planet as data source
     */
    public void makeSnapshot(ResourcePlotDTO plotDTO, Planet planet) {
        HashMap<String, Integer> resourceAmounts;
        Integer cycle = planet.getCycleCount();
        String className;
        Integer stock;
        SingleResourceDataDTO plotData = new SingleResourceDataDTO();

        //guard
        if (plotDTO == null) {
            plotDTO = new ResourcePlotDTO();
        }

        //guard
        if (planet == null) {
            return;
        }


        resourceAmounts = planet.getAllResourceAmounts();

        // loop over all resources
        for (Map.Entry<String, Integer> element : resourceAmounts.entrySet()) {
            className = element.getKey();
            stock = element.getValue();

            // case resource still not exist - create the node
            if (plotDTO.plotData.containsKey(className) == false) {
                plotData = new SingleResourceDataDTO();
                plotData.className = className;
                plotDTO.plotData.put(className, plotData);
            }

            // case resource still exist - just extract the node
            if (plotDTO.plotData.containsKey(className) == true) {
                plotData = plotDTO.plotData.get(className);
            }

            // add to list
            plotData.measureMap.put(cycle, stock);

        }


    }

}
