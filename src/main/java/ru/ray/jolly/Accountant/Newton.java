package ru.ray.jolly.Accountant;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Newton {
    private static ArrayList<Double> separateDiff = new ArrayList<>();

    public static void fillDiff(List<Pair<Double, Double>> index){
        separateDiff = new ArrayList<>();
        LinkedList<Pair<Double, Double>> list = new LinkedList<>();
        for (Pair<Double, Double> doublePair : index) {
            list.add(doublePair);
            separateDiff.add(makerDiff(list));
        }
    }

    private static double makerDiff(LinkedList<Pair<Double, Double>> list){
        int size = list.size();
        switch (size){
            case (1): return list.getFirst().getValue();
            case (2): return (list.getLast().getValue() - list.getFirst().getValue())/(list.getLast().getKey() - list.getFirst().getKey());
            default:
                LinkedList<Pair<Double, Double>> left = new LinkedList<>(list), right = new LinkedList<>(list);
                left.removeFirst();
                right.removeLast();
                return (makerDiff(left)-makerDiff(right))/(list.getLast().getKey() - list.getFirst().getKey());
        }
    }

    public static double getPol(double x, List<Pair<Double, Double>> list){

        double sum = separateDiff.get(0);
        double koef = 1;
        for (int i = 1; i < list.size(); i++){
            koef *= (x - list.get(i-1).getKey());
            sum += koef * separateDiff.get(i);
        }
        return sum;
    }
}
