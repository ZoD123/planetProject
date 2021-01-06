package stellar.resource;

import stellar.IResourceAddable;
import stellar.IResourceConsumable;

public abstract class Resource implements IResourceAddable, IResourceConsumable {
    private ResourceHandler resourceHandler;
    private Integer value;

    /**
     * constructor for creating a ressource field
     *
     * @param resourceAmount ammount of ressource
     */
    public Resource(int resourceAmount) {
        this.value = resourceAmount;
        resourceHandler = new ResourceHandler();
    }

    /**
     * get the Stock value of the ressource
     *
     * @return the stock
     */
    public Integer getStock() {

        return value;
    }

    /**
     * consumes the amount from the resource (if possible)
     *
     * @param value amount of resource which will be consumed
     * @return residual amount
     */
    @Override
    public Integer consume(Integer value) throws ResourceEmptyExeption {
        if (this.value < value) {
            throw new ResourceEmptyExeption();
        }

        if (this.value >= value) {
            this.value = this.value - value;
            return this.value;
        }

        return 0;
    }

    /**
     * produced amount is saved and will be added later on (after cycle is completed)
     * @param value amount of resource which will be produced
     */
    @Override
    public void addAsync(Integer value) {
        resourceHandler.putResourceToAddMap(this, value);
    }

    /**
     * produces the amount of resource
     * @param value amount of produced resource
     */
    protected Integer addValue(Integer value){
        this.value = this.value + value;
        return this.value;
    }

    public void setResourceHandler(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

}