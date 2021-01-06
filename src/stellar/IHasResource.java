package stellar;


import stellar.resource.Resource;

/**
 * interface for managing resources
 */
public interface IHasResource {
    public void addResource(Resource resourceToAdd);

    public Object getResource(Class type, Integer minAmount);
}
