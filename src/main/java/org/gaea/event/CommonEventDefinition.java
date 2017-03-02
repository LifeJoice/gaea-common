package org.gaea.event;

/**
 * 一些通用事件的常量定义。
 * <p>
 *     一般来说,不应该放在这里.但没有统一的命名服务,放在公用包用起来方便.
 *     例如,避免同一个事件名称定义多处.
 * </p>
 * Created by iverson on 2016/12/7.
 */
public interface CommonEventDefinition {
    /* ************************************ 事件CODE 名称定义 ************************************ */
    public static final String EVENT_CODE_XML_DATASET_LOAD_FINISHED = "GAEA_EVENT_DATA_DATASET_LOAD_FINISHED"; // 加载XML DATASET完成

    public static final String EVENT_FRAMEWORK_SPRING = "spring"; // spring内置的事件处理机制
    public static final String EVENT_FRAMEWORK_REDIS = "kafka"; // 基于kafka的事件处理机制
}
