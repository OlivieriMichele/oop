/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ListBuilderFactoryImpl implements ListBuilderFactory {

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl(new ArrayList<T>());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        List<T> list = new ArrayList<>();
        list.add(start);
        builderList.forEach(e -> e.build().forEach(elem -> list.add(elem)));
        list.add(stop);
        return new ListBuilderImpl<>(list);
    }

    private final class ListBuilderImpl<T> implements ListBuilder<T>{
        private final List<T> list;

        public ListBuilderImpl(List<T> list) {
            this.list = Collections.unmodifiableList(list);
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            List<T> newList = new ArrayList<>(this.list);
            list.forEach(e -> newList.add(e));
            return new ListBuilderImpl<>(newList);
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            List<T> newList = new ArrayList<>(this.list);
            lb.build().forEach(e -> newList.add(e));
            return new ListBuilderImpl<>(newList);
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            List<T> newList = new ArrayList<>();
            for(var elem : this.list){
                if(elem.equals(t)){
                    newList.addAll(lb.build());
                } else {
                    newList.add(elem);
                }
            }
            return new ListBuilderImpl<>(newList);
        }

        @Override
        public ListBuilder<T> reverse() {
            List<T> newList = new LinkedList<>();
            for(int i = this.list.size()-1; i>=0; i--){
                newList.add(this.list.get(i));
            }
            return new ListBuilderImpl<>(newList);
        }

        @Override
        public List<T> build() {
            return Collections.unmodifiableList(this.list);
        }
    }   
}