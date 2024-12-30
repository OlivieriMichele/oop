package it.unibo.e5;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class TimetableFactoryImpl implements TimetableFactory{

    public TimetableFactoryImpl() {
    }

    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    private static class TimetableImpl implements Timetable{

        @Override
        public Set<String> rooms() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Set<String> courses() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<Integer> hours() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Timetable addBooking(String room, String course, Day day, int hour, int duration) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Optional<Integer> findPlaceForBooking(String room, Day day, int duration) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<Integer, String> getDayAtRoom(String room, Day day) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Optional<Pair<String, String>> getDayAndHour(Day day, int hour) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<Day, Map<Integer, String>> getCourseTable(String course) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
