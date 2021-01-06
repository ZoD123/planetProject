package stellar.planet;

import stellar.ResourceDTO.ResourceDTOConstructor;
import stellar.ResourceDTO.ResourcePlotDTO;
import stellar.ICyclable;
import stellar.IHasResource;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Planet implements IHasResource, Runnable {

    private Thread thread;
    private PlanetarySystem planetarySystem;
    private final Integer maxCycles = 500;
    private UUID planetID;
    private ResourceHandler resourceHandler;
    private Integer cycleCount;
    private ResourcePlotDTO resourcePlotDTO;


    /**
     * Represents the information for all living organisms on the planet
     */
    private HashMap<UUID, ICyclable> wildlive;

    private ArrayList<ICyclable> wildLiveToKill;
    private ArrayList<ICyclable> wildLiveNewLiveToAdd;

    /**
     * public constructor
     *
     * @param seed determine the seed which is used to create initial values
     */
    public Planet(ArrayList<Resource> seed, PlanetarySystem system) {
        planetarySystem = system;
        this.planetID = UUID.randomUUID();
        this.cycleCount = 0;
        wildlive = new HashMap<UUID, ICyclable>();
        resourceHandler = new ResourceHandler();
        wildLiveToKill = new ArrayList<ICyclable>();
        wildLiveNewLiveToAdd = new ArrayList<ICyclable>();
        resourcePlotDTO = new ResourcePlotDTO();

        for (Resource resource : seed) {
            resourceHandler.addResource(resource);
        }

    }

    /**
     * delivers (if possible) the requested ressource from the planet
     *
     * @param minAmount //TODO MARCO KOMMENTIEREN
     * @param type      //TODO MARCO KOMMENTIEREN
     * @return the requested ressource
     */
    @Override
    public Object getResource(Class type, Integer minAmount) {
        Object object = resourceHandler.getResource(type, minAmount);
        return object;
    }

    /**
     * adds a resource to Planet
     *
     * @param resourceToAdd
     */
    @Override
    public void addResource(Resource resourceToAdd) {
        resourceHandler.addResource(resourceToAdd);
    }

    /**
     * Adds a new living organism to a list and these organisms are initialized in the end of cycle
     * @param element the object which will be living
     */
    public void lifeReceived(ICyclable element) {
        wildLiveNewLiveToAdd.add(element);
    }

    /**
     * Kills the no longer living organism from planet
     *
     * @param object which now is dead :-(
     */
    public void lifeKilled(ICyclable object) {
        wildLiveToKill.add(object);
    }

    /**
     * World experience one cycle
     *
     * @throws DeathWorldException world now is dead :-(
     */
    private void cycling() {


        while(cycleCount < maxCycles) {
            cycleCount++;
            String output;

            ResourceDTOConstructor resourceDTOConstructor = new ResourceDTOConstructor();
            resourceDTOConstructor.makeSnapshot(resourcePlotDTO, this);

            for (ICyclable element : wildlive.values()) {
                element.dayDream();
            }

        wildLiveToKillCleanUp();
        wildLiveAddNewLive();
        resourceHandler.cleanUpResourceToAddMap();

            if (wildlive.size() < 1) {

                synchronized (planetarySystem) {
                    planetarySystem.planetDied(this);
                }
                break;
            }
        }


    }

    /**
     * returns the name of the planet
     *
     * @return name of the planet
     */
    public UUID getPlanetID() {
        return planetID;
    }

    /**
     * returns the number of current living organisms.
     *
     * @return number of living organisms.
     */
    public Integer getNumberOfLivingOrganism() {
        if (wildlive == null) {
            throw new NullPointerException();
        }

        return wildlive.size();
    }

    /**
     * returns a string that represents the status of the resource handler
     *
     * @return
     */
    public String planetStatusUpdate() {
        return resourceHandler.resourceStatusUpdate() + "Wildlife Status: " + wildlive.size();
    }

    /**
     * clean up the kill List which holds the life which is died.
     */
    private void wildLiveToKillCleanUp() {
        for (ICyclable element : wildLiveToKill
        ) {
            wildlive.remove(element.GetUUID());
        }
        wildLiveToKill.clear();
    }

    /**
     * Initializes a new living organism
     */
    public void wildLiveAddNewLive() {
        for (ICyclable element : wildLiveNewLiveToAdd) {
            wildlive.put(element.GetUUID(), element);
        }
        wildLiveNewLiveToAdd.clear();
    }

    /**
     * returns the cycle count which the planet is
     *
     * @return the cycle information
     */
    public Integer getCycleCount() {
        return cycleCount;
    }


    /**
     * get all resources the planet at the moment have
     *
     * @return hashmap - Key = ClassName, Value = Stock
     */
    public HashMap<String, Integer> getAllResourceAmounts() {
        return resourceHandler.getAllResourceAmounts();
    }

    /**
     * show the chart.
     */
    public void showChart() {
        ChartModule chartModule = chartModule = new ChartModule(resourcePlotDTO);
        chartModule.showChart();
    }

    /**
     * implementation of Runnable interface - multithreading
     */
    @Override
    public void run() {

            cycling();

            if (cycleCount > 5) {
                showChart();
            }


    }

    /**
     * starts the thread
     */
    public void start(){
        if(thread != null){
            return;
        }
        thread = new Thread(this);
        thread.start();
    }
}
