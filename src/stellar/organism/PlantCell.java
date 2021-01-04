package stellar.organism;

import stellar.resource.Oxygen;
import stellar.planet.Planet;
import stellar.resource.Carbon;
import stellar.resource.CarbonDioxide;

public class PlantCell extends Organism {

    /**
     * public constructor
     *
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public PlantCell(Planet planet) {
        super(planet);
        super.requiredResource.put(CarbonDioxide.class, 1);
        super.producedResource.put(Oxygen.class, 2);
        super.producedResource.put(Carbon.class, 1);
    }

    /**
     * reproduction - a new cell is born.
     */
    @Override
    protected void reproduce() {
        new PlantCell(super.planet);
        super.reproductionThreshold = 0;
    }

    @Override
    protected void defineLifeProperties() {
        super.starvationRange = 1000;
        super.starvationThreshold = 0;
        super.starvationThresholdIncrease = 500;
        super.reproductionRange = 5000;
        super.reproductionThreshold = 0;
        super.reproductionThresholdIncrease = 10;
    }
}
