/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


public class WindowingFactoryImpl implements WindowingFactory {

    private <X,Y> Windowing<X,Y> generic(Predicate<List<X>> ready, Function<List<X>,Y> Mapper){
        return new Windowing<X, Y>(){
            private final List<X> data = new LinkedList<>();

            @Override
            public Optional<Y> process(X x) {
                this.data.add(x);
                if (ready.test(this.data)){
                    return Optional.of(Mapper.apply(data));
                } else {
                    return Optional.empty();
                }
            }
        };
    }

    @Override
    public <X> Windowing<X, X> trivial() {
        return generic(e -> !e.isEmpty(), e -> e.get(e.size()-1));
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return generic(e -> e.size()>1, e -> new Pair<>(e.get(e.size()-2),e.get(e.size()-1)));
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return generic(e -> e.size()>3, e -> e.get(e.size()-1)+e.get(e.size()-2)+e.get(e.size()-3)+e.get(e.size()-4));
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return generic(e -> e.size()>=n, e -> e.subList(e.size()-n, e.size()));
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return generic(
            e -> e.stream().mapToInt(Integer::intValue).sum()>=n, 
            e -> mapper(n,e));
    }

    private List<Integer> mapper(Integer n, List<Integer> list){
        int sum = 0;
        List<Integer> result = new LinkedList<>();
        for (int i = list.size() - 1; i >= 0; i--) { // Scansiona la lista al contrario
            sum += list.get(i);
            result.add(0, list.get(i)); // Aggiunge all'inizio
            if (sum >= n) {
                break; // Ferma la ricerca appena si raggiunge la somma richiesta
            }
        }
        return result;
    }
}