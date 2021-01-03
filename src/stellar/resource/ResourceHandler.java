package stellar.resource;

import stellar.IHasResource;
import stellar.planet.ClassNameExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ResourceHandler implements IHasResource {
    private HashMap<Class, ArrayList<Resource>> resourceTypeMap;

    public ResourceHandler() {
        resourceTypeMap = new HashMap<Class, ArrayList<Resource>>();
    }

    @Override
    /**
     * adds resource to a resource managed structure
     */
    public void addResource(Resource resourceToAdd) {
        // query resourceTypeMap if class type (e.g. Oxygen) is already key of HashMap
        // if yes add resource object to ArrayList
        // if not create new key:value in HashMap

        Class resourceClass = resourceToAdd.getClass();
        boolean containsResourceClass = resourceTypeMap.containsKey(resourceClass);

        if (containsResourceClass == true) {
            ArrayList resourceList = resourceTypeMap.get(resourceClass);
            resourceList.add(resourceToAdd);

            return;
        }

        if (containsResourceClass == false) {
            ArrayList resourceList = new ArrayList();
            resourceList.add(resourceToAdd);
            resourceTypeMap.put(resourceClass, resourceList);

            return;
        }

    }

    /**
     * returns requested resource with at least the requested amount from resource managed structure
     */
    @Override
    public Resource getResource(Class type, Integer minAmount) {
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
        return resultResource;
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

}