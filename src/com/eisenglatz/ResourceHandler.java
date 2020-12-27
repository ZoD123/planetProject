package com.eisenglatz;

import java.util.HashMap;
import java.util.ArrayList;


public class ResourceHandler implements IHasResource {
    private HashMap<Class,ArrayList<Resource>> resourceTypeMap;

    public ResourceHandler() {
        resourceTypeMap = new HashMap<Class,ArrayList<Resource>>();
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
    public Resource getResource(Class type, Integer minAmount){
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

    public Integer getFieldSize(Class resourceClass) {
        ArrayList resourceList = resourceTypeMap.get(resourceClass);
        if(resourceList == null){
            return 0;
        }
        Integer resourceListSize = resourceList.size();
        return resourceListSize;
    }

}