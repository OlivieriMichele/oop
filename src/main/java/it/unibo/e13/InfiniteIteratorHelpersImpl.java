/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e13;

import java.util.ArrayList;
import java.util.List;


public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {
    
    public class Generic<X> implements InfiniteIterator<X>{
        protected final List<X> data;

        public Generic(List<X> list){
            this.data = new ArrayList<>(list);
        }

        @Override
        public X nextElement() {
            X temp = this.data.remove(0);
            this.data.add(this.data.size(),temp);
            return temp;
        }

    }

    @Override
    public <X> InfiniteIterator<X> of(X x) {
        List<X> list = new ArrayList<>();
        list.add(x);
        return new Generic(list);
    }

    @Override
    public <X> InfiniteIterator<X> cyclic(List<X> l) {
        return new Generic<>(l);
    }

    @Override
    public InfiniteIterator<Integer> incrementing(int start, int increment) {
        List<Integer> init = new ArrayList<>();
        init.add(start);
        return new Generic<Integer>(init){
            
            @Override
            public Integer nextElement() {
                int temp = this.data.remove(0);
                this.data.add(this.data.size(),temp+increment);
                return temp;                
            }
        };
    }

    @Override
    public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j) {
        return new InfiniteIterator<X>(){
            private boolean first = true; 
            
            @Override
            public X nextElement() {
                if(first){
                    this.first = false;
                    return i.nextElement();
                } else {
                    this.first = true;
                    return j.nextElement();
                }
            }
        };
    }

    @Override
    public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
        return new InfiniteIterator<>() {
            private final List<X> buffer = new ArrayList<>();
    
            @Override
            public List<X> nextElement() {
                while (buffer.size() < n) {
                    buffer.add(i.nextElement());
                }
                List<X> window = new ArrayList<>(buffer);
                buffer.remove(0);
                buffer.add(i.nextElement());
                return window;
            }
        };
    }
}