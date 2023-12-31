package com.java.concurrency.disruptor.factory;

import com.java.concurrency.disruptor.entity.LongEvent;
import com.lmax.disruptor.EventFactory;

/**
 * LongEventFactory实例化LongEvent
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    //实例化对象
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
