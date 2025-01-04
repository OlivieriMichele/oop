/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e6;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class LogicImpl implements Logic {
    private List<Position> marks;
    private final Integer size;
    private boolean isMoving;
    private Integer left = 0;
    private Integer up = 0;

    public LogicImpl(int size) {
        this.size = size;
        this.isMoving = false;
        this.marks = new LinkedList<>();
    }

    @Override
    public void Hit(Position pos) {
        if(!isOver()){
            if(this.isMoving){
                if(this.modifyDirectrion(pos)){
                    this.move();
                }
            } else {
                this.putMarks(pos);
            }
        }
    }

    private void putMarks(Position pos) {
        for(int i=-2; i<=2; i++){
            for(int j=-2; j<=2; j++){
                this.marks.add(new Position(pos.x()+i, pos.y()+j));
            }
        }
        this.isMoving = true;
    }

    @Override
    public Optional<String> getMarks(Position pos) {
        return this.marks.stream().anyMatch(e -> e.equals(pos)) ? Optional.of("*") : Optional.empty();
    }

    private void move() {
        this.marks = this.marks.stream().map(e -> new Position(e.x()+left, e.y()+up)).toList();
    }

    private boolean modifyDirectrion(Position pos) {
        if (pos.x() == this.size-1){
            this.left = 1;
            this.up = 0;
            return true;
        } else if(pos.x() == 0){
            this.left = -1;
            this.up = 0;
            return true;
        } else if(pos.y() == this.size-1){
            this.up = 1;
            this.left = 0;
            return true;
        } else if(pos.y() == 0){
            this.up = -1;
            this.left = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        return this.marks.stream().anyMatch(e -> e.x()>=size || e.x()<0 || e.y()<0 || e.y()>=size);
    }
}