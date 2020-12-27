package com.eisenglatz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Organism  implements ICyclable{
    private Planet planet;
    private UUID guid;
    protected HashMap<Class, Integer> requiredResource;
    protected HashMap<Class, Resource> availableResource;
    protected HashMap<Class, Integer> producedResource;
    protected Integer starvationFactor; // > 1000 life is killed

    /**
     * public constructor
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public Organism(Planet planet) {

        this.planet = planet;
        this.guid = UUID.randomUUID();
        this.planet.lifeReceived(this);
        this.requiredResource = new HashMap<Class, Integer>();
        this.availableResource = new HashMap<Class,Resource>();
        this.producedResource = new HashMap<Class,Integer>();
        this.starvationFactor = 0;
    }

    /**
     * organisms eats, which activates metabolism and printing of new resource amounts
     */
    public void eat() throws RessourceEmptyExeption {
        ArrayList<Class> missingResources = detectMissingResources();

        if (starvationFactor > 1000) {
            planet.lifeKilled(this);
        }

        if (missingResources.size() > 0 ) {
            // increase starvation approximation
            starvationFactor += 5 * (5+starvationFactor);

            // try get new resource
            tryGetNewResources(missingResources);
            return;
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
    private void metabolismTransformation () throws RessourceEmptyExeption {
        Resource resource;
        Integer amount;
        Class resourceClass;
        initializeResultResources();


        // consume resources for each required resource entry
        for (Map.Entry<Class,Integer> element: requiredResource.entrySet()
        ) {
            resourceClass = element.getKey();
            resource = availableResource.get(resourceClass);
            amount = element.getValue();
            resource.consume(amount);
        }

        // produce resources for each produces resource entry
        for (Map.Entry<Class,Integer> element: producedResource.entrySet()
        ) {
            resourceClass = element.getKey();
            resource = availableResource.get(resourceClass);
            amount = element.getValue();
            resource.produce(amount);
        }
    }

    /**
     * initialize resources which will be produced
     */
    private void initializeResultResources() {
        for (Map.Entry<Class,Integer> element: producedResource.entrySet()
        ) {

            Class carbonDioxideClass = element.getKey();
            Resource carbonDioxideObject;

            // check if resource always available, than use it
            if (availableResource.containsKey(carbonDioxideClass) == true) {
                continue;
            }

            // if not available, try get resource from planet
            carbonDioxideObject = planet.getResource(carbonDioxideClass);
            if (carbonDioxideObject instanceof Resource == true) {
                availableResource.put(carbonDioxideClass, carbonDioxideObject);
                continue;
            }

            //if reached this new resource must be created
            carbonDioxideObject = new CarbonDioxide(0);
            availableResource.put(carbonDioxideClass, carbonDioxideObject);
            continue;
        }
    }

}
