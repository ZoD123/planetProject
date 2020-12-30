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

    /**
     * public constructor
     * @param seed determine the seed which is used to create initial values
     */
    public Planet(String PlanetName, ArrayList<Resource> Seed) {
        this.planetName = PlanetName;
        wildlive = new HashMap<UUID, ICyclable>();
        resourceHandler = new ResourceHandler();

        for (Resource resource: Seed) {
            resourceHandler.addResource(resource);
        }
    }

    /**
     * delivers (if possible) the requested ressource from the planet
     * @param resourceOfInterest the ressource type the requester want to have
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
     * @param object the object which will be living
     */
    public void lifeReceived(ICyclable object) {
        wildlive.put(object.GetUUID(), object);
    }

    /**
     * Kills the no longer living organism from planet
     * @param object which now is dead :-(
     */
    public void lifeKilled(ICyclable object){
        wildlive.remove(object.GetUUID());
    }

    /**
     * World experience one cycle
     * @throws DeathWorldException world now is dead :-(
     */
    public void cycling() throws DeathWorldException {
        String output;

        for ( ICyclable element: wildlive.values() )  {
            element.dayDream();
        }

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
    public String resourceStatusUpdate(){
        return resourceHandler.resourceStatusUpdate();
    }
}
