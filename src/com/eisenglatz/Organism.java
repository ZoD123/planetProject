package com.eisenglatz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Organism  implements ICyclable{
    private Planet planet;
    private UUID guid;
    private HashMap<Class, Integer> requiredResource;
    private HashMap<Class, Resource> availableResource;
    private Integer starvationFactor; // > 1000 life is killed

    /**
     * public constructor
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public Organism(Planet planet) {

        this.planet = planet;
        this.guid = UUID.randomUUID();
        this.planet.lifeReceived(this);
    }

    /**
     * organisms eats, which activates metabolism and printing of new resource amounts
     */
    public void eat() throws RessourceEmptyExeption {
        ArrayList<Class> missingResources = detectMissingResources();
        if (missingResources.size() > 0 ) {
            // increase starvation approximation
            starvationFactor += 5 * (5+starvationFactor);

            // try get new resource
            tryGetNewResources(missingResources);
        }

        //reset starvationFactor
        starvationFactor = 0;

        try {
            metabolismTransformation();
        } catch(Exception ex){

        }


    }

    /**
     * get the own UUID
     * @return
     */
    @Override
    public UUID GetUUID() {
        return guid;
    }

    /**
     * experience one cycle
     */
    @Override
    public void dayDream() {
       try {
           eat();

       } catch (RessourceEmptyExeption e) {
            planet.lifeKilled(this);
        }
    }

    /**
     * detect missing resources which the current object has
     * @return
     */
    private ArrayList<Class> detectMissingResources(){
        ArrayList<Class> missingResources = new ArrayList<Class>();
        for (Map.Entry<Class,Integer> element : requiredResource.entrySet()) {
            Class type = element.getKey();
            Integer value = element.getValue();

            // if one key is not included
            if (availableResource.containsKey(type) == false) {
                missingResources.add(type);
                continue;
            }

            // if stock of availabel resource type is not enough
            if(availableResource.get(type).getStock() < value){
                missingResources.add(type);
                continue;
            }
        }
        // now all and types are available
        return missingResources;
    }

    /**
     * tries to get the missing resources from the planet
     * @param requestesResources
     */
    private void tryGetNewResources(ArrayList<Class> requestesResources) {
        Resource requestedResource;

        for (Class type : requestesResources) {
            availableResource.remove(type);
            requestedResource = planet.getResource(type);

            if (requestedResource == null) {
                continue;
            }
            availableResource.put(type, requestedResource);
        }

    }

    /**
     * defines the metabolism transformation which runs over the available resources
     * @throws RessourceEmptyExeption
     */
    private void metabolismTransformation () throws RessourceEmptyExeption{
        // TO BE OVERWRITTEN
    };


}
