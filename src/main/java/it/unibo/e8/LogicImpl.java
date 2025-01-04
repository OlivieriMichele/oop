/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e8;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.e2.Position;


public class LogicImpl implements  Logic {
    private final List<Position> data = new LinkedList<>();

    public LogicImpl(int size) {
    }

    @Override
    public void hit(Position pos) {
        if (!isOver()){
            if (!grid(pos)){
                this.data.add(pos);
            }
        }
    }

    @Override
    public Optional<String> getMarks(Position pos) {
        return grid(pos)? Optional.of("*") : this.data.stream().anyMatch(e -> e.equals(pos)) ? Optional.of("o") : Optional.empty();
    }

    private boolean grid(Position pos){
        return pos.x() % 2 != 0 || pos.y() % 2 != 0;
    }

    private boolean isNear4(Integer p1, Integer p2, Integer p3, Integer p4){
        return isNear(p1, p2) && isNear(p1, p3) && isNear(p1, p4) && isNear(p2, p3) && isNear(p2, p4)&& isNear(p4, p3);
    }

    private boolean isNear(Integer p1, Integer p2){
        return Math.abs(p1 - p2) <= 2;
    }

    @Override
    public boolean isOver() {
        if (this.data.size() < 4) return false;
        return isNear4(data.get(data.size()-1).x(), data.get(data.size()-2).x(), 
            data.get(data.size()-3).x(), data.get(data.size()-4).x()) 
            && isNear4(data.get(data.size()-1).y(), data.get(data.size()-2).y(), 
            data.get(data.size()-3).y(), data.get(data.size()-4).y());
    }

}
