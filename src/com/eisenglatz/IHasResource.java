package com.eisenglatz;

//TODO Ina: Kommentar!
public interface IHasResource {
    public void addResource(Resource resourceToAdd);
    public Resource getResource(Class type, Integer minAmount);
}
