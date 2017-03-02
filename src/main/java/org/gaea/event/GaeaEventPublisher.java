package org.gaea.event;

import org.gaea.exception.SysInitException;

import java.util.Map;

/**
 * Created by iverson on 2016/12/6.
 */
public interface GaeaEventPublisher {
    public void publishSimpleEvent(String eventCode, Map<String,Object> eventContext) throws SysInitException;
}
