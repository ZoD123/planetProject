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
        String wildLifeNode = "Wildlife";
        HashMap<String, Integer> resourceAmounts;
        Integer cycle = planet.getCycleCount();
        String className;
        Integer stock;
        Integer wildLifeStock;
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

        // get wildlife stock
        wildLifeStock = planet.getNumberOfLivingOrganism();

        // case wildlife still not exist - create the node
        if (plotDTO.plotData.containsKey(wildLifeNode) == false) {
            plotData = new SingleResourceDataDTO();
            plotData.className = wildLifeNode;
            plotDTO.plotData.put(wildLifeNode, plotData);
        }

        // case wildlife still exist - just extract the node
        if (plotDTO.plotData.containsKey(wildLifeNode) == true) {
            plotData = plotDTO.plotData.get(wildLifeNode);
        }

        plotData.measureMap.put(cycle,wildLifeStock);


    }

}
