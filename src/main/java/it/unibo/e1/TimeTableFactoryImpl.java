package it.unibo.e1;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class TimeTableFactoryImpl implements TimetableFactory{

    @Override
    public Timetable empty() {
        return new TimetableImpl(new HashMap<>());
    }

    @Override
    public Timetable single(String activity, String day) {
        Map<String,Map<String,Integer>> data = new HashMap<>();
        data.putIfAbsent(activity, new HashMap<>());
        data.get(activity).put(day, 1);
        return new TimetableImpl(data);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Map<String, Map<String,Integer>> data = new HashMap<>();

        Set<String> activities = new HashSet<>(table1.activities());
        activities.addAll(table2.activities());

        Set<String> days = new HashSet<>(table1.days());
        days.addAll(table2.days());

        for(var activity : activities){
            Map<String,Integer> dayMap = new HashMap<>();
            for(var day : days) {
                int sum = table1.getSingleData(activity, day) + table2.getSingleData(activity, day);
                if(sum>0){
                    dayMap.put(day, sum);
                }
            }
            if(!dayMap.isEmpty()) {
                data.put(activity, dayMap);
            }
        }

        return new TimetableImpl(data);
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Map<String, Map<String,Integer>> data = new HashMap<>();
        Set<String> activities = new HashSet<>(table.activities());
        Set<String> days = new HashSet<>(table.days());

        for(var activity : activities){
            Map<String,Integer> dayHour = new HashMap<>();
            for(var day : days) {
                if( bounds.apply(activity,day) > table.getSingleData(activity, day)) {
                    dayHour.put(day, table.getSingleData(activity, day));
                } else{
                    dayHour.put(day, bounds.apply(activity, day));
                }
            }
            if(!dayHour.isEmpty()){
                data.put(activity, dayHour);
            }
        }

        return new TimetableImpl(data);
    }

    private static class TimetableImpl implements Timetable {
        private final Map<String, Map<String, Integer>> data;

        TimetableImpl(Map<String, Map<String, Integer>> data) {
            this.data = new HashMap<>();
            for (var entry : data.entrySet()) {
                this.data.put(entry.getKey(), new HashMap<>(entry.getValue()));
            }
        }

        @Override
        public Timetable addHour(String activity, String day) {
            Map<String, Map<String, Integer>> newData = new HashMap<>(this.data);
            newData.putIfAbsent(activity, new HashMap<>());
            newData.get(activity).merge(day, 1, Integer::sum);
            return new TimetableImpl(newData);
        }

        @Override
        public Set<String> activities() {
            return Collections.unmodifiableSet(data.keySet());
        }

        @Override
        public Set<String> days() {
            Set<String> days = new HashSet<>();
            data.values().forEach(map -> days.addAll(map.keySet()));
            return days;
        }

        @Override
        public int getSingleData(String activity, String day) {
            return data.getOrDefault(activity, Collections.emptyMap()).getOrDefault(day, 0);
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            return activities.stream()
                .flatMap(activity -> days.stream().map(day -> getSingleData(activity, day)))
                .mapToInt(Integer::intValue)
                .sum();
        }
    }
}
