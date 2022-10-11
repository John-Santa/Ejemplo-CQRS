package com.banking.cqrs.core.infrastructure;

import com.banking.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<? extends BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvent(String aggregateId);
}
