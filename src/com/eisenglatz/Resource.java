package com.eisenglatz;

public abstract class Resource {

    private Integer value;

    /**
     * constructor for creating a ressource field
     * @param resourceAmount ammount of ressource
     */
    public Resource(int resourceAmount) {
        this.value = resourceAmount;
    }

    /**
     * get the Stock value of the ressource
     * @return the stock
     */
    public Integer getStock() {

        return value;
    }

    /**
     * consumes the amount from the resource (if possible)
     * @param amount amount of resource which will be consumed
     * @return residual amount
     */
    public int consume(int amount) throws RessourceEmptyExeption {
        if (this.value < amount) {
            throw new RessourceEmptyExeption();
        }

        if (this.value >= amount){
            this.value = this.value - amount;
            return this.value;
        }

        return 0;
    }
    /**
     * produces the amount of resource
     * @param amount amount of resource which will be produced
     * @return new amount
     */
    public int produce(int amount) {
        this.value = this.value + amount;
        return this.value;
    }

}