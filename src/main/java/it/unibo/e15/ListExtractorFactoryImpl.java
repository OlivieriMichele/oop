/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e15;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


public class ListExtractorFactoryImpl implements ListExtractorFactory {
    
    private <X,Y> ListExtractor<X, List<Y>> generic(Predicate<X> stopCondition, Predicate<X> startCondition, Function<List<X>,Y> mapper) {
        return new ListExtractor<X, List<Y>>(){
            public List<X> data;

            @Override
            public List<Y> extract(List<X> list) {
                this.data = new ArrayList<>();
                List<Y> result = new ArrayList<>();
                boolean start = false;
                for(var elem : list) {
                    if(startCondition.test(elem)){
                        start = true;
                    }
                    if (!stopCondition.test(elem) && start){
                        data.add(elem);
                        result.add(mapper.apply(data));
                    } else if (stopCondition.test(elem)){
                        return result;
                    }
                }
                return result;
            }
        };
    }

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return (List<X> list) -> {
            if (!list.isEmpty()) {
                return Optional.of(list.get(0));
            } else {
                return Optional.empty();
            }
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return generic(stopCondition, e -> e.equals(e), e -> mapper.apply(e.get(e.size()-1)) );
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
        return generic(e -> !e.equals(e), startCondition, e -> {
            List<X> list = new ArrayList<>();
            list.addAll(e);
            return list;
        } );
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
