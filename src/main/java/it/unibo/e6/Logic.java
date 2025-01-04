/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e6;

import java.util.Optional;


interface Logic {

    void Hit(Position pos);
    
    Optional<String> getMarks(Position pos);
    
    boolean isOver();
}
