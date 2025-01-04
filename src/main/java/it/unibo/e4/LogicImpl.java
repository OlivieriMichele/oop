/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e4;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class LogicImpl implements Logic {
    private List<Position> marks;
    private final Integer size;
    private final Integer limit = 5;
    private Integer direction = -1;

    public LogicImpl(int size) {
        this.size = size;
        this.marks = new LinkedList<>();
    }

    @Override
    public void hit(Position pos) {
        if (!isOver()){
            if (this.marks.size()<this.limit){
                this.marks.add(pos);
            } else {
                this.move();
            }
        }
    }

    @Override
    public Optional<Integer> getmark(Position pos) {
        return Optional.of(this.marks.indexOf(pos)).filter(i -> i >= 0).map(i -> i + 1);
    }

    private void move() {
        this.checkdirection();
        this.marks = this.marks.stream()
            .map(e -> new Position(e.x() + this.direction, e.y()))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public boolean isOver() {
        return this.marks.stream().anyMatch(e -> e.x()>=this.size);
    }

    private void checkdirection() {
        if (this.marks.stream().anyMatch(e -> e.x()<=0) ){
            this.direction = 1;
        }
    }
}