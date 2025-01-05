/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.prova.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.e2.Position;


public class LogicImpl implements Logic{
    private final Integer size;
    private final List<Position> data;
    private Integer border = 0;

    public LogicImpl(int size) {
        this.size = size;
        this.data = new LinkedList<>();
    }

    @Override
    public void hit(Position pos) {
        if (!isOver()){
            if(this.data.size() < 2){
                this.data.add(pos);
            } else {
                border++;
            }
        }
    }

    @Override
    public Optional<String> getMarks(Position pos) {
        if (!this.data.isEmpty() && pos.equals(this.data.get(0))) {
            return Optional.of("1");
        }
        if (this.data.size() > 1 && pos.equals(this.data.get(1))) {
            return Optional.of("2");
        }
        return this.fill(pos);
    }

    private Optional<String> fill(Position pos){
        return isInRange(pos) ? Optional.of("o") : Optional.empty();
    }

    private boolean isInRange(Position pos){
        return  pos.x() >= this.data.get(0).x() - border && 
                pos.x() <= this.data.get(1).x() + border && 
                pos.y() >= this.data.get(0).y() - border && 
                pos.y() <= this.data.get(1).y() + border;
    }

    @Override
    public boolean isOver() {
        return  this.data.size() < 2 ? false : 
                this.data.get(0).x() - border < 0 || 
                this.data.get(1).x() + border > this.size-1 || 
                this.data.get(0).y() - border < 0 || 
                this.data.get(1).y() + border > this.size-1;
    }

}
