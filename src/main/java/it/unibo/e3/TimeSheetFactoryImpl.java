package it.unibo.e3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSheetFactoryImpl implements TimeSheetFactory{

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        List<String> activities = new ArrayList<>();
        for(int i=1; i<=numActivities; i++){
            activities.add("act"+i);
        }

        List<String> days = new ArrayList<>();
        for(int i=1; i<=numNames; i++){
            days.add("day"+i);
        }

        List<List<Integer>> data = new ArrayList<>();
        for(int i=0; i<numActivities; i++){
            List<Integer> row = new ArrayList<>(Collections.nCopies(numNames, hours));
            data.add(row);
        }

        return new TimeSheetImpl(activities,days,data);
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetImpl(activities,days,data);
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        List<String> activities = new ArrayList<>();
        for(int i=1; i<=numActivities; i++){
            activities.add("act"+i);
        }

        List<String> days = new ArrayList<>();
        for(int i=1; i<=numDays; i++){
            days.add("day"+i);
        }

        List<List<Integer>> dati = new ArrayList<>();
        for(int i=0; i<numActivities; i++){
            List<Integer> row = new ArrayList<>(Collections.nCopies(numDays, 0));
            dati.add(row);
        }
        
        for(var entry : data){
            dati.get(entry.get1())
                .set(entry.get2(), dati.get(entry.get1()).get(entry.get2()) +1);
        }

        return new TimeSheetImpl(activities,days,dati);
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        
        List<List<Integer>> dati = new ArrayList<>();
        for (@SuppressWarnings("unused")String activity : activities) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(days.size(), 0));
            dati.add(row);
        }
        for(var entry : data.entrySet()){
            dati.get(activities.indexOf(entry.getKey().get1()))
                .set(days.indexOf(entry.getKey().get2()), entry.getValue());
        }

        return new TimeSheetImpl(activities,days,dati);
    }

    private static class TimeSheetImpl implements TimeSheet{
        private final List<String> activities;
        private final List<String> days;
        private final List<List<Integer>> data;

        public TimeSheetImpl(List<String> activities, List<String> days, List<List<Integer>> data){
            this.activities = new ArrayList<>(activities);
            this.days = new ArrayList<>(days);
            this.data = new ArrayList<>();
            for(List row : data){
                this.data.add(row);
            }
        }

        @Override
        public List<String> activities() {
            return this.activities;
        }

        @Override
        public List<String> days() {
            return this.days;
        }

        @Override
        public int getSingleData(String activity, String day) {
            int i = this.activities.indexOf(activity);
            int j = this.days.indexOf(day);
            if (i==-1 || j==-1) return 0;
            return this.data.get(i).get(j);
        }

        @Override
        public Map<String, Integer> sumsPerActivity() {
            Map<String,Integer> map = new HashMap<>();
            for(var acticity : this.activities){
                map.put(acticity, this.data.get(this.activities.indexOf(acticity)).stream().mapToInt(Integer::intValue).sum());
            }
            return map;
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            Map<String,Integer> map = new HashMap<>();
            for(var day : this.days){
                map.put(day, this.data.stream().map(e -> e.get(this.days.indexOf(day))).mapToInt(Integer::intValue).sum());
            }
            return map;
        }
    }
}