/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class LogicImpl implements Logic {
    private final Integer size; 
    private List<Position> marks = new LinkedList<>();
    private boolean isMoving;

    public LogicImpl(int size) {
        this.size = size;
        this.isMoving = false;
    }

    @Override
    public void hit(Position pos) {
        if (!isOver()){
            if (this.isMoving || isStartingMoving(pos)){
                this.moveMarks();
                return;
            }
            this.marks.add(pos);
        }
    }

    @Override
    public Optional<Integer> mark(Position pos) {
        return Optional.of(this.marks.indexOf(pos)).filter(i -> i >= 0).map(i -> i + 1);
    }

    @Override
    public boolean isOver() {
        return this.marks.stream().anyMatch(e -> e.x()==this.size || e.y()==-1);
    }

    private boolean isStartingMoving(Position pos) {
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if(this.marks.contains(new Position(pos.x()+i, pos.y()+j))){
                    this.isMoving = true; 
                    return true;
                }
            }
        }
        return false;
    }

    private void moveMarks() {
        this.marks = this.marks.stream()
            .map(e -> new Position(e.x()+1,e.y()-1))
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
