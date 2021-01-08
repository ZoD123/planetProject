package stellar.resource;

import stellar.IHasResource;
import stellar.planet.ClassNameExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ResourceHandler implements IHasResource {
    private HashMap<Class, ArrayList<Resource>> resourceTypeMap;
    private HashMap<Resource, Integer> resourceToAddMap;

    public ResourceHandler() {
        resourceTypeMap = new HashMap<Class, ArrayList<Resource>>();
        resourceToAddMap = new HashMap<Resource, Integer>();
    }

    /**
     * adds resource to a resource managed structure
     */
    @Override
    public void addResource(Resource resourceToAdd) {
        // query resourceTypeMap if class type (e.g. Oxygen) is already key of HashMap
        // if yes add resource object to ArrayList
        // if not create new key:value in HashMap

        Class resourceClass = resourceToAdd.getClass();
        boolean containsResourceClass = resourceTypeMap.containsKey(resourceClass);

        if (containsResourceClass == true) {
            ArrayList resourceList = resourceTypeMap.get(resourceClass);
            resourceList.add(resourceToAdd);
        }

        if (containsResourceClass == false) {
            ArrayList resourceList = new ArrayList();
            resourceList.add(resourceToAdd);
            resourceTypeMap.put(resourceClass, resourceList);
        }

        resourceToAdd.setResourceHandler(this);
    }

    /**
     * returns requested resource with at least the requested amount from resource managed structure
     */
    @Override
    public Object getResource(Class type, Integer minAmount) {
        // try to iterate over ArrayList and return first Resource element whose Resource.value >= min Amount
        // if no Resource.value of any Resource element contains the minAmount -> return null object

        Resource resultResource = null;

        boolean containsResourceClass = resourceTypeMap.containsKey(type);
        if (containsResourceClass == false) {
            return null;
        }

        for (Resource resource : resourceTypeMap.get(type)) {
            Integer availableAmount = resource.getStock();
            if (availableAmount < minAmount) {
                continue;
            }
            resultResource = resource;
            break;
        }
        return (Object) resultResource;
    }

    /**
     * returns amount/ size of fields of a requested resource type
     */
    public Integer getFieldSize(Class resourceClass) {
        ArrayList resourceList = resourceTypeMap.get(resourceClass);
        if (resourceList == null) {
            return 0;
        }
        Integer resourceListSize = resourceList.size();
        return resourceListSize;
    }

    /**
     * returns an information as string over the overall resoruce state of the handler
     *
     * @return the status as string
     */
    public String resourceStatusUpdate() {
        ClassNameExtractor classNameExtractor = new ClassNameExtractor();
        Class className;
        String classNameString;
        String output = "";
        Integer ResourceValue;

        for (Map.Entry<Class, ArrayList<Resource>> mapElement : resourceTypeMap.entrySet()
        ) {
            ResourceValue = 0;
            className = mapElement.getKey();
            classNameString = classNameExtractor.getExtractedClassName(className);

            for (Resource resourceElement : mapElement.getValue()
            ) {
                ResourceValue += resourceElement.getStock();
            }

            output += classNameString + ": " + ResourceValue + " | ";

        }
        return output;

    }

    /**
     * returns a List which holds the stock Information for all resources.
     *
     * @return hashmap - Key = ClassName, Value = Stock
     */
    public HashMap<String, Integer> getAllResourceAmounts() {
        HashMap<String, Integer> resourceStatus = new HashMap<String, Integer>();
        ClassNameExtractor extractor = new ClassNameExtractor();
        ArrayList<Resource> resourceList;
        Class type;
        String className;
        Integer stock;

        for (Map.Entry<Class, ArrayList<Resource>> element : resourceTypeMap.entrySet()
        ) {
            type = element.getKey();
            resourceList = element.getValue();

            stock = 0;
            className = extractor.getExtractedClassName(type);

            for (Resource resource : resourceList
            ) {
                stock += resource.getStock();
            }

            resourceStatus.put(className, stock);

        }
        return resourceStatus;
    }

    /**
     * saves produced amount in a management system until it will be added to resource.value
     *
     * @param resource which gets the produced value amount
     * @param value    amount of resource which will be produced
     */
    protected HashMap putResourceToAddMap(Resource resource, Integer value) {
        // if resource object already available as key in HashMap, add the newly produced value to the previous one
        // if resource object is not available then create new entry
        boolean containsResourceClass = resourceToAddMap.containsKey(resource);

        if (containsResourceClass == true) {
            Integer newValue = resourceToAddMap.get(resource) + value;
            resourceToAddMap.put(resource, newValue);

            return resourceToAddMap;
        }

        if (containsResourceClass == false) {
            resourceToAddMap.put(resource, value);

            return resourceToAddMap;
        }

        return resourceToAddMap;
    }

    /**
     * adds all produced amounts of the respective resource to resource.value
     */
    public HashMap cleanUpResourceToAddMap() {
        for (Resource resource : resourceToAddMap.keySet()) {
            Integer valueToAdd = resourceToAddMap.get(resource);
            resource.addValue(valueToAdd);
        }

        // delete all entries such that HashMap is clean for the next cycle
        resourceToAddMap.clear();

        return resourceToAddMap;
    }
}