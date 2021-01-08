package stellar.organism;

import stellar.resource.Oxygen;
import stellar.planet.Planet;
import stellar.resource.Carbon;
import stellar.resource.CarbonDioxide;

public class AnimalCell extends Organism {

    /**
     * public constructor
     *
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public AnimalCell(Planet planet) {
        super(planet);
        super.requiredResource.put(Oxygen.class, 4);
        super.requiredResource.put(Carbon.class, 2);
        super.producedResource.put(CarbonDioxide.class, 2);
    }

    /**
     * reproduction - a new cell is born.
     */
    @Override
    protected void reproduce() {
        new AnimalCell(super.planet);
        super.reproductionThreshold = 0;
    }

    @Override
    protected void defineLifeProperties() {
        super.starvationRange = 1000;
        super.starvationThreshold = 0;
        super.starvationThresholdIncrease = 500;
        super.reproductionRange = 1000;
        super.reproductionThreshold = 0;
        super.reproductionThresholdIncrease = 100;
    }

}
