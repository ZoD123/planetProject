package stellar;

import stellar.resource.Resource;

import java.util.HashMap;

/**
 * interface for adding resource
 */
public interface IResourceAddable {
    public HashMap<Resource, Integer> addAsync(Integer value);
}
