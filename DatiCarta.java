package com.mercatopokemon.Tables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class DatiCarta {
    private final SimpleObjectProperty<LocalDate> dataO;
    private final SimpleIntegerProperty pop;
    private final SimpleDoubleProperty min;
    private final SimpleDoubleProperty med;

    public DatiCarta(LocalDate dataO, int pop, double min, double med) {
        this.dataO = new SimpleObjectProperty<>(dataO);
        this.pop = new SimpleIntegerProperty(pop);
        this.min = new SimpleDoubleProperty(min);
        this.med = new SimpleDoubleProperty(med);
    }

    public LocalDate getDataO() {
        return dataO.get();
    }

    public int getPop() {
        return pop.get();
    }

    public double getMin() {
        return min.get();
    }

    public double getMed() {
        return med.get();
    }
}

