/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e2;

import java.util.Optional;


interface  Logic {

    void hit(Position pos);
    
    Optional<Integer> mark(Position pos);

    boolean isOver();

}