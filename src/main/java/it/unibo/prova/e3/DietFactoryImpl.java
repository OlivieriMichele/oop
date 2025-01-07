/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.prova.e3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return dietImpl(1500, 2000, e -> true);
    }

    @Override
    public Diet lowCarb() {
        return dietImpl(1000, 1500, e -> e.get(0) <= 300);
    }

    @Override
    public Diet highProtein() {
        return dietImpl(2000, 2500, e -> e.get(0) <= 300 && e.get(1) >= 1300);
    }

    @Override
    public Diet balanced() {
        return dietImpl(1600, 2000, e -> e.get(0)>=600 && e.get(2)>=400 && e.get(1)+e.get(2) <= 1100);
    }

    private Diet dietImpl(int minRange, int maxRange, Predicate<List<Double>> check){

        return new Diet(){
            private final Map<String,Map<Nutrient,Integer>> food = new HashMap<>();
            private final Integer min = minRange;
            private final Integer max = maxRange;
    
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                this.food.putIfAbsent(name, nutritionMap);
            }
    
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                List<Double> nutrient = new LinkedList<>();
                nutrient.add(.0);
                nutrient.add(.0);
                nutrient.add(.0);
                for(var elem : dietMap.entrySet()){
                    if (this.food.containsKey(elem.getKey())){
                        nutrient.set(0, nutrient.get(0) + this.food.get(elem.getKey()).get(Nutrient.CARBS)*elem.getValue()/100);
                        nutrient.set(1, nutrient.get(1) + this.food.get(elem.getKey()).get(Nutrient.PROTEINS)*elem.getValue()/100);
                        nutrient.set(2, nutrient.get(2) + this.food.get(elem.getKey()).get(Nutrient.FAT)*elem.getValue()/100);
                    }
                }
                return check.test(nutrient) && checkCalories(nutrient);
            }

            private boolean checkCalories(List<Double> list){
                Double sum = list.stream().mapToDouble(Double::doubleValue).sum();
                return sum >= this.min && sum <= this.max;
            }

        };        
    }
}