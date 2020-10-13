package ru.ray.jolly.Accountant;

import javafx.util.Pair;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.LinkedList;

public class Calculator {
    public Calculator(double x0, double y0, double x1, double accuracy, int id){
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.accuracy = accuracy;
        this.id = id;
        this.step = (x1-x0)/1000;
    }

    private double x0, y0, x1, accuracy, step;
    private int id;
    public XYDataset getDataset(){
        LinkedList<Pair<Double, Double>> valueList = getRungeStartTable();
        int edgeIndex = valueList.size();
        modifMilnFinishTable(valueList);
        XYSeries series = new XYSeries("");
//        for (Pair<Double, Double> pair: valueList)
//            series.add(pair.getKey(), pair.getValue());
        for (int i = valueList.size()/2; i < valueList.size(); i++)
            series.add(valueList.get(i).getKey(), valueList.get(i).getValue());
        for (int i = valueList.size()/2; i < valueList.size()-6; i++){
            if ((edgeIndex-5 > i || edgeIndex+5 < i)) {
                int start = Math.max(i - 4, 0);
                int finish = Math.min(i + 4, valueList.size() - 1);
                Newton.fillDiff(valueList.subList(start, finish));
                double step = (valueList.get(i + 1).getKey() - valueList.get(i).getKey()) / 5;
                double pos = valueList.get(i).getKey();
                for (int j = 1; j < 5; j++) {
                    pos += step;
                    series.add(pos, Newton.getPol(pos, valueList.subList(start, finish)));
                }
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private void modifMilnFinishTable(LinkedList<Pair<Double, Double>> valueList){
        int lastIndex = (int)Math.round((x1-x0)/step)+1;
        for (int i = 4; i < lastIndex; i++){
            double f3 = Functions.chooserFunction(id, valueList.get(i-3).getKey(), valueList.get(i-3).getValue());
            double f2 = Functions.chooserFunction(id, valueList.get(i-2).getKey(), valueList.get(i-2).getValue());
            double f1 = Functions.chooserFunction(id, valueList.get(i-1).getKey(), valueList.get(i-1).getValue());
            double f0 =Functions.chooserFunction(id, valueList.get(i-1).getKey() + step,
                    valueList.get(i-4).getValue() + 4.0/3*step*(f3*2+f2+2*f1));
            double corY = f0;
            do {
                f0 = corY;
                corY = valueList.get(i-2).getValue()+step/3*(f2+4*f1+f0);
            }while (Math.abs(corY-f0)/29 > accuracy);
            valueList.add(new Pair<>(valueList.get(i-1).getKey()+step, corY));
        }
        System.out.println(valueList.getLast().getKey());
    }

    private LinkedList<Pair<Double, Double>> getRungeStartTable(){
        LinkedList<Pair<Double, Double>> startPack = new LinkedList<>();
        startPack.add(new Pair<>(x0, y0));
        double nextX = x0, nextY = y0;
//        for (int i = 0; i<4; i++){
//            nextY = getNewY(nextX, nextY);
//            nextX+=step;
//            startPack.add(new Pair<>(nextX, nextY));
//        }
        while (nextX < x1) {
            nextY = getNewY(nextX, nextY);
            nextX+=step;
            startPack.add(new Pair<>(nextX, nextY));
        }
        return startPack;
    }

    private double getNewY(double x, double y){
        double k0 = Functions.chooserFunction(id, x, y);
        double k1 = Functions.chooserFunction(id, x+step / 2, y+k0*step / 2);
        double k2 = Functions.chooserFunction(id, x+step / 2, y+k1*step / 2);
        double k3 = Functions.chooserFunction(id, x+step, y+k2*step);
        return y + step*(k0 + 2*k1 + 2*k2 + k3) / 6;
    }
}
