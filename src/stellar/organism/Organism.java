package stellar.organism;

import stellar.ICyclable;
import stellar.IResourceConsumable;
import stellar.planet.Planet;
import stellar.planet.TimeExpiredEvaluator;
import stellar.resource.Resource;
import stellar.resource.ResourceEmptyExeption;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public abstract class Organism implements ICyclable {
    protected Planet planet;
    protected Integer starvationThreshold;
    protected Integer starvationThresholdIncrease;
    protected Integer starvationRange;
    protected Integer reproductionThresholdIncrease;
    protected Integer reproductionThreshold;
    protected Integer reproductionRange;
    protected HashMap<Class, Integer> requiredResource;
    protected HashMap<Class, Resource> availableResource;
    protected HashMap<Class, Integer> producedResource;
    private UUID guid;
    private TimeExpiredEvaluator timeExpiredEvaluator;
    private TimeExpiredEvaluator reproductionTimeExpiredEvaluator;


    /**
     * public constructor
     *
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public Organism(Planet planet) {
        defineLifeProperties();
        this.planet = planet;
        this.guid = UUID.randomUUID();
        this.planet.lifeReceived(this);
        this.requiredResource = new HashMap<Class, Integer>();
        this.availableResource = new HashMap<Class, Resource>();
        this.producedResource = new HashMap<Class, Integer>();
        this.timeExpiredEvaluator = new TimeExpiredEvaluator(starvationRange);
        this.reproductionTimeExpiredEvaluator = new TimeExpiredEvaluator(reproductionRange);


    }

    /**
     * class creates a new object of itsef.
     */
    protected abstract void reproduce();


    /**
     * organisms eats, which activates metabolism and printing of new resource amounts
     */
    public void eat() {
        ArrayList<Class> missingResources = detectMissingResources();

        // life of organism ends - klass mark as "died" - will remove in next cycle
        if (timeExpiredEvaluator.checkLifeIsExpired(starvationThreshold) == false) {
            planet.lifeKilled(this);
            return;
        }

        // resources out of stock - increase starvation
        if (missingResources.size() > 0) {
            // increase starvation approximation
            starvationThreshold += starvationThreshold * 2 + starvationThresholdIncrease;

            // try get new resource
            tryGetNewResources(missingResources);
            return;
        }


        try {
            metabolismTransformation();
            if (starvationThreshold < 500) {
                starvationThreshold = 0;
            }
            if (starvationThreshold > 500) {
                starvationThreshold -= starvationThresholdIncrease;
            }
        } catch (Exception ex) {

        }

        if (checkForReproduction() == false) {
            return;
        }
        reproduce();
    }

    /**
     * get the own UUID
     *
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

        eat();


    }

    /**
     * detect missing resources which the current object has
     *
     * @return
     */
    private ArrayList<Class> detectMissingResources() {
        ArrayList<Class> missingResources = new ArrayList<Class>();
        for (Map.Entry<Class, Integer> element : requiredResource.entrySet()) {
            Class type = element.getKey();
            Integer value = element.getValue();

            // if one key is not included
            if (availableResource.containsKey(type) == false) {
                missingResources.add(type);
                continue;
            }

            // if stock of availabel resource type is not enough
            if (availableResource.get(type).getStock() < value) {
                missingResources.add(type);
                continue;
            }
        }
        // now all and types are available
        return missingResources;
    }

    /**
     * tries to get the missing resources from the planet
     *
     * @param requestesResources
     */
    private void tryGetNewResources(ArrayList<Class> requestesResources) {
        Object object;
        Class objectClass;
        Resource requestedResource = null;
        Integer requestedAmount;
        boolean isCorrectResourceType;
        boolean isCorrectCastable;

        for (Class type : requestesResources) {
            availableResource.remove(type);

            requestedAmount = requiredResource.get(type);
            object = planet.getResource(type, requestedAmount);
            if (object == null){
                return;
            }

            objectClass = object.getClass();
            isCorrectResourceType = objectClass.isAssignableFrom(type);
            //   isCorrectCastable = objectClass.isAssignableFrom(IResourceConsumable.class);
            isCorrectCastable = object instanceof IResourceConsumable;
            if (isCorrectResourceType == true && isCorrectCastable == true) {
                requestedResource = (Resource) object;
            }

            if (requestedResource == null) {
                continue;
            }

            availableResource.put(type, requestedResource);
        }

    }

    /**
     * defines the metabolism transformation which runs over the available resources
     *
     * @throws ResourceEmptyExeption
     */
    private void metabolismTransformation() throws ResourceEmptyExeption {
        Resource resource;
        Integer amount;
        Class resourceClass;
        initializeResultResources();


        // consume resources for each required resource entry
        for (Map.Entry<Class, Integer> element : requiredResource.entrySet()
        ) {
            resourceClass = element.getKey();
            resource = availableResource.get(resourceClass);
            amount = element.getValue();
            resource.consume(amount);
        }

        // produce resources for each produces resource entry
        for (Map.Entry<Class, Integer> element : producedResource.entrySet()
        ) {
            resourceClass = element.getKey();
            resource = availableResource.get(resourceClass);
            amount = element.getValue();
            resource.addAsync(amount);
            // die zu hinzufügende Resource ist jetzt im ResourceHandler der Resource abespeichert
            // aber nicht im ResourceHandler des Planeten
        }
    }

    /**
     * initialize resources which will be produced
     */
    private void initializeResultResources() {
        Constructor dynamicConstructor;
        Class resultResourceClass;
        Resource resourceObject = null;
        Object object;
        Class objectClass;
        boolean isCorrectResourceType;
        boolean isCorrectCastable;

        for (Map.Entry<Class, Integer> element : producedResource.entrySet()
        ) {

            resultResourceClass = element.getKey();

            // check if resource always available, than use it
            if (availableResource.containsKey(resultResourceClass) == true) {
                continue;
            }

            // if not available, try get resource from planet
            object = planet.getResource(resultResourceClass, 0);
            objectClass = object.getClass();
            isCorrectResourceType = objectClass.isAssignableFrom(resultResourceClass);
            isCorrectCastable = object instanceof IResourceConsumable;

            if (isCorrectResourceType == true && isCorrectCastable == true) {
                resourceObject = (Resource)object;
            }

            if (resourceObject instanceof Resource == true) {
                availableResource.put(resultResourceClass, resourceObject);
                continue;
            }

            //if reached this new resource must be created carbonDioxideObject
            try {
                dynamicConstructor = resultResourceClass.getConstructor(Integer.class);
                object = dynamicConstructor.newInstance(0);

                if (object instanceof Resource == false){
                    continue;
                }
                resourceObject = (Resource)object;
                availableResource.put(objectClass,resourceObject);

            } catch (Exception methodException) {
                System.out.println(methodException.getMessage());
                return;
            }


            planet.addResource(resourceObject);
            continue;
        }
    }

    /**
     * checks if organism is capable to reproduce
     */
    private boolean checkForReproduction() {
        // no reproduction if cell is starving (not starving -> starvationThreshold == 0)
        // reset also reproductionThreshold
        if (starvationThreshold > 0) {
            reproductionThreshold = 0;
            return false;
        }
        //
        if (reproductionTimeExpiredEvaluator.checkLifeIsExpired(reproductionThreshold) == true) {
            // if randomizer decides not to reproduce increase reproduction approximation
            // such that probability for reproduction in the next round increases
            reproductionThreshold += reproductionThresholdIncrease;
            return false;
        }
        return true;
    }

    protected abstract void defineLifeProperties();

}
