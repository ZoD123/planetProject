package com.eisenglatz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Planet implements IHasResource {

    private String planetName;
    private ResourceHandler resourceHandler;

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
        wildlive = new HashMap<UUID, ICyclable>();
        resourceHandler = new ResourceHandler();
        wildLiveToKill = new ArrayList<ICyclable>();
        wildLiveNewLiveToAdd = new ArrayList<ICyclable>();

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
        String output;
        wildLiveToKill.clear();
        wildLiveNewLiveToAdd.clear();

        for ( ICyclable element: wildlive.values() )  {
            element.dayDream();
        }

        wildLiveToKillCleanUp();
        wildLiveAddNewLive();

        if(wildlive.size() < 1) {
            throw new DeathWorldException(this);
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
    public Integer GetNumberOfLivingOrganism() {
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
}
