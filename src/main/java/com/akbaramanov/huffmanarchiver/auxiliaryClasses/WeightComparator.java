package com.akbaramanov.huffmanarchiver.auxiliaryClasses;

import java.util.Comparator;

public class WeightComparator implements Comparator<Pair<Byte, Integer>> {

    @Override
    public int compare(Pair<Byte, Integer> o1, Pair<Byte, Integer> o2) {
        return (o1.getValue()-o2.getValue());
    }
}
