/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.prova.e4;

import java.util.Optional;

import it.unibo.e2.Position;


interface Logic {

    void hit();

    Optional<String> getMarks(Position pos);

    boolean isOver();

}
