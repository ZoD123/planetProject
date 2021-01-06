package stellar;

import stellar.resource.ResourceEmptyExeption;

/**
 * interface for consuming resource
 */
public interface IResourceConsumable {
    public Integer consume(Integer value) throws ResourceEmptyExeption;
}
