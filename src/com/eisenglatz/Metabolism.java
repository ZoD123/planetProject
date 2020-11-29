package com.eisenglatz;

public class Metabolism {
    private Resource carbonField;
    private Resource oxygenField;
    private Resource carbonDioxideField;

    public Metabolism(Planet planetField) {
        Resource carbonField = planetField.getResource("carbon");
        Resource oxygenField = planetField.getResource("oxygen");
        Resource carbonDioxideField = planetField.getResource("carbonDioxide");
    }

    /**
     * transforms 1 carbon + 2 oxygen -> 2 carbonDioxide
     */
    public void transform() {
        carbonField.consume(1);
        oxygenField.consume(2);
        carbonDioxideField.produce(1);
    }
}
