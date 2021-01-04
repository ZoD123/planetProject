package stellar.organism;

import stellar.ICyclable;
import stellar.planet.Planet;
import stellar.planet.TimeExpiredEvaluator;
import stellar.resource.CarbonDioxide;
import stellar.resource.Resource;
import stellar.resource.ResourceEmptyExeption;

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
    public void eat() throws ResourceEmptyExeption {
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
            starvationThreshold = 0;
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
        try {
            eat();

        } catch (ResourceEmptyExeption e) {
            planet.lifeKilled(this);
        }
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
        Resource requestedResource;
        Integer requestedAmount;

        for (Class type : requestesResources) {
            availableResource.remove(type);

            requestedAmount = requiredResource.get(type);
            requestedResource = planet.getResource(type, requestedAmount);

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
            resource.produce(amount);
        }
    }

    /**
     * initialize resources which will be produced
     */
    private void initializeResultResources() {
        Class carbonDioxideClass;
        Resource carbonDioxideObject;


        for (Map.Entry<Class, Integer> element : producedResource.entrySet()
        ) {

            carbonDioxideClass = element.getKey();

            // check if resource always available, than use it
            if (availableResource.containsKey(carbonDioxideClass) == true) {
                continue;
            }

            // if not available, try get resource from planet
            carbonDioxideObject = planet.getResource(carbonDioxideClass, 0);
            if (carbonDioxideObject instanceof Resource == true) {
                availableResource.put(carbonDioxideClass, carbonDioxideObject);
                continue;
            }

            //if reached this new resource must be created
            carbonDioxideObject = new CarbonDioxide(0);
            availableResource.put(carbonDioxideClass, carbonDioxideObject);
            planet.addResource(carbonDioxideObject);
            continue;
        }
    }

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
