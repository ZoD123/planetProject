package com.eisenglatz;

public interface IHasResource {
    public void addResource(Resource resourceToAdd);
    public Resource getResource(Class type, Integer minAmount);
}
