package it.unibo.e5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import it.unibo.e5.Timetable.Day;


public class TimetableFactoryImpl implements TimetableFactory{

    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    private static class TimetableImpl implements Timetable{

        Map<Day, Map<Integer,Pair<String,String>>> schedule;

        public TimetableImpl(){
            this.schedule = new HashMap<>();
            for(Day day : Day.values()){
                this.schedule.put(day, new HashMap<>());
            }
        }

        @Override
        public Set<String> rooms() {
            Set<String> room = new HashSet<>();
            for(var entry : schedule.entrySet()){
                for( var map : entry.getValue().entrySet()){
                    room.add(map.getValue().get1());
                }
            }
            return room;
        }

        @Override
        public Set<String> courses() {
            Set<String> courses = new HashSet<>();
            for(var entry : schedule.entrySet()){
                for( var map : entry.getValue().entrySet()){
                    courses.add(map.getValue().get2());
                }
            }
            return courses;
        }

        @Override
        public List<Integer> hours() {
            Set<Integer> hours = new TreeSet<>();
            for(var entry : schedule.values()){
                hours.addAll(entry.keySet());
            }
            return new ArrayList<>(hours);
        }

        @Override
        public Timetable addBooking(String room, String course, Day day, int hour, int duration) {
            TimetableImpl table = new TimetableImpl();
            table.schedule.putAll(schedule);
            for (int i = 0; i < duration; i++) {
                table.schedule.get(day).put(hour + i, new Pair<>(room, course));
            }
            return table;
        }

        @Override
        public Optional<Integer> findPlaceForBooking(String room, Day day, int duration) {
            Map<Integer, Pair<String,String>> map = this.schedule.get(day);
            for (int i=9; i < 18-duration; i++){
                boolean available = true;
                for (int j=0; j < duration; j++){
                    if(map.containsKey(i+j) && map.get(i+j).get1().equals(room)){
                            available = false;
                    }
                }
                if (available){
                    return Optional.of(i);
                }
            }
            return Optional.empty();
        }

        @Override
        public Map<Integer, String> getDayAtRoom(String room, Day day) {
            Map<Integer, String> coursHour = new HashMap<>();
            this.schedule.get(day).entrySet().stream()
                        .filter(e -> e.getValue().get1().equals(room))
                        .forEach(e -> coursHour.put(e.getKey(), e.getValue().get2()));
            return coursHour;
        }

        @Override
        public Optional<Pair<String, String>> getDayAndHour(Day day, int hour) {
            if (schedule.get(day).containsKey(hour)) {
                return Optional.of(schedule.get(day).get(hour));
            }
            return Optional.empty();
        }

        @Override
        public Map<Day, Map<Integer, String>> getCourseTable(String course) {
            Map<Day, Map<Integer,String>> map = new HashMap<>();
            for(Day day : Day.values()){
                Map<Integer,String> value = new HashMap<>();
                this.schedule.get(day).entrySet().stream()
                    .filter(e -> e.getValue().get2().equals(course))
                    .forEach(e -> value.put(e.getKey(), e.getValue().get1()));
                if(!value.isEmpty()){
                    map.put(day, value);
                }
            }
            return map;
        }

    }
}
