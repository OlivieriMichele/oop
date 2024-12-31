/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unibo.e7;

import java.util.List;


public class ListBuilderFactoryImpl implements ListBuilderFactory {

    @Override
    public <T> ListBuilder<T> empty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
