package org.gaea.data.jackson;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 这个是ObjectMapper在做转换的时候，动态过滤哪些字段不需要转换的类过滤器的壳。
 * 注意：这是一个壳！没有任何字段，是因为字段是动态添加的。
 * Created by iverson on 2017/8/9.
 */
@JsonFilter("gaeaJacksonFilter")
public class GaeaJacksonPropertyFilterMixIn {
}
