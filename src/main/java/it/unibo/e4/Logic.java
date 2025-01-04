package it.unibo.e4;

import java.util.Optional;

public interface Logic {

    void hit(Position pos);

    Optional<Integer> getmark(Position pos);

    boolean isOver();
}