/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.prova.e4;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.e2.Position;


public class LogicImpl implements Logic{
    private final List<Position> data;
    private final Integer size;
    private final List<Position> direction;
    private Position current;

    public LogicImpl(int size) {
        this.size = size;
        this.data = new LinkedList<>();
        this.current= new Position(0, -1);
        this.direction = new LinkedList<>();
        this.direction.add(new Position(0, -1));
        this.direction.add(new Position(1, 0));
        this.direction.add(new Position(0, 1));
        this.direction.add(new Position(-1, 0));
    }

    @Override
    public void hit() {
        if(this.data.isEmpty()){
            Random rand = new Random();
            this.data.add(new Position(rand.nextInt(size-1), rand.nextInt(size-1)));
        } else if (!isOver()) {
            this.data.add(getPosition());
        }
    }

    @Override
    public Optional<String> getMarks(Position pos) {
        return this.data.contains(pos) ? Optional.of(String.valueOf(this.data.indexOf(pos))) : Optional.empty();
    }
    
    private Position getPosition(){
        return new Position(this.data.get(data.size()-1).x()+current.x(), 
                            this.data.get(data.size()-1).y()+current.y());
    }

    private boolean checkDirection(Position pos){
        return outOfMap(pos) || this.data.contains(pos);
    }

    private boolean outOfMap(Position pos){
        return pos.x()<0 || pos.x() >= this.size || pos.y()<0 || pos.y() >= this.size;
    }

    @Override
    public boolean isOver() {
        Position last = this.data.get(data.size()-1);
        Position newdir = current;
        for(int i=0; i<4; i++){
            if (!checkDirection(new Position(last.x()+newdir.x(), last.y()+newdir.y())) ){
                this.current = newdir;
                return false;
            }
            newdir = this.direction.get( (this.direction.indexOf(this.current) +1) % 4);
        }
        return true;
    }

}
