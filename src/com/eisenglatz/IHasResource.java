package com.eisenglatz;


/**
 * interface for managing resources
 */
public interface IHasResource {
    public void addResource(Resource resourceToAdd);
    public Resource getResource(Class type, Integer minAmount);
}
