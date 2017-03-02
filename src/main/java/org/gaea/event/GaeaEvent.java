package org.gaea.event;

import java.io.Serializable;

/**
 * 定义事件的接口。
 * Created by iverson on 2016-12-6 16:19:03.
 */
public interface GaeaEvent<T> extends Serializable {
    String getEventCode();
    T getEventContext();
//    void setEventContext(T eventContext);
}
