package com.eisenglatz;

import eisenglatz.ResourceDTO.ResourceDTOConstructor;
import eisenglatz.ResourceDTO.ResourcePlotDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Planet implements IHasResource {

    private String planetName;
    private ResourceHandler resourceHandler;
    private Integer cycleCount;
    private ResourcePlotDTO resourcePlotDTO;


    /**
     *  Represents the information for all living organisms on the planet
     */
    private HashMap<UUID,ICyclable> wildlive;

    private ArrayList<ICyclable> wildLiveToKill;
    private ArrayList<ICyclable> wildLiveNewLiveToAdd;

    /**
     * public constructor
     * @param seed determine the seed which is used to create initial values
     */
    public Planet(String PlanetName, ArrayList<Resource> seed
    ) {
        this.planetName = PlanetName;
        this.cycleCount = 0;
        wildlive = new HashMap<UUID, ICyclable>();
        resourceHandler = new ResourceHandler();
        wildLiveToKill = new ArrayList<ICyclable>();
        wildLiveNewLiveToAdd = new ArrayList<ICyclable>();
        resourcePlotDTO = new ResourcePlotDTO();

        for (Resource resource: seed) {
            resourceHandler.addResource(resource);
        }

    }

    /**
     * delivers (if possible) the requested ressource from the planet
     * @param minAmount //TODO MARCO KOMMENTIEREN
     * @param type //TODO MARCO KOMMENTIEREN
     * @return the requested ressource
     */
    @Override
    public Resource getResource(Class type, Integer minAmount){
        Resource resource = resourceHandler.getResource(type, minAmount);
        return resource;
    }

    /**
     * adds a resource to Planet
     * @param resourceToAdd
     */
    @Override
    public void addResource(Resource resourceToAdd) {
        resourceHandler.addResource(resourceToAdd);
    }

    /**
     * Planet received a new living organism which is able to live at least one cycle :-)
     * @param element the object which will be living
     */
    public void lifeReceived(ICyclable element) {
        wildLiveNewLiveToAdd.add(element);
    }

    /**
     * Kills the no longer living organism from planet
     * @param object which now is dead :-(
     */
    public void lifeKilled(ICyclable object){
        wildLiveToKill.add(object);
    }

    /**
     * World experience one cycle
     * @throws DeathWorldException world now is dead :-(
     */
    public void cycling() throws DeathWorldException {
        cycleCount++;
        String output;

        wildLiveToKill.clear();
        wildLiveNewLiveToAdd.clear();

        ResourceDTOConstructor resourceDTOConstructor = new ResourceDTOConstructor();
        resourceDTOConstructor.makeSnapshot(resourcePlotDTO,this);

        for ( ICyclable element: wildlive.values() )  {
            element.dayDream();
        }

        wildLiveToKillCleanUp();
        wildLiveAddNewLive();

        if(wildlive.size() < 1) {
            throw new DeathWorldException(this,"World starved after " + cycleCount + " Cycles" );
        }

    }

    /**
     * returns the name of the planet
     * @return name of the planet
     */
    public String getPlanetName() {
        return planetName;
    }

    /**
     * returns the number of current living organisms.
     * @return number of living organisms.
     */
    public Integer getNumberOfLivingOrganism() {
        if(wildlive == null)
        {
            throw new NullPointerException();
        }

        return wildlive.size();
    }

    /**
     * returns a string that represents the status of the resource handler
     * @return
     */
    public String planetStatusUpdate(){
        return resourceHandler.resourceStatusUpdate() + "Wildlife Status: " + wildlive.size();
    }

    /**
     * clean up the kill List which holds the life which is died.
     */
    private void wildLiveToKillCleanUp(){
        for (ICyclable element : wildLiveToKill
             ) {
            wildlive.remove(element.GetUUID());
        }
    }

    public void wildLiveAddNewLive() {
        for (ICyclable element : wildLiveNewLiveToAdd) {
            wildlive.put(element.GetUUID(), element);
        }
    }

    /**
     * returns the cycle count which the planet is
     * @return the cycle information
     */
    public Integer getCycleCount(){
        return cycleCount;
    }


    /**
     * get all resources the planet at the moment have
     * @return hashmap - Key = ClassName, Value = Stock
     */
    public HashMap<String,Integer> getAllResourceAmounts(){
        return resourceHandler.getAllResourceAmounts();
    }

    /**
     * show the chart.
     */
    public void showChart(){
        ChartModule chartModule = chartModule = new ChartModule(resourcePlotDTO);
        chartModule.showChart();
    }
}
