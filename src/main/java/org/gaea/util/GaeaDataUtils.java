package org.gaea.util;

import org.apache.commons.lang3.StringUtils;
import org.gaea.exception.ProcessFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by iverson on 2017年12月7日 星期四
 */
public class GaeaDataUtils {
    private static final Logger logger = LoggerFactory.getLogger(GaeaDataUtils.class);

    /**
     * 通过一个变量路径，例如["user","role"]，获取origData中的对应位置的数据（origData.get("user").get("role")）。
     * <p>
     *     举例：<br/>
     *     对于JSON数据，我们可以通过getData(jsonMap, new String[]{"lv1","lv2","data"})获得数据部分的值。
     * </p>
     * <p>
     *     {
             "lv1": {
                 "lv2": {
                     "success": true,
                     "data": [{"id":549,"schemaName":"【年终大促】3件7折","schemaCode":"171129155941895","schemaType":2,"status":1},{"id":548,"schemaName":"【年终大促】2件8折","schemaCode":"171129155750936","schemaType":2,"status":1}]
                 }
             }
           }
     * </p>
     * @param origData
     * @param paramPathArray
     * @return
     * @throws ProcessFailedException
     */
    public static Object getData(Map origData, String[] paramPathArray) throws ProcessFailedException {
        if(origData==null || paramPathArray==null){
            return origData;
        }
//        Map loopMap = origData;
        Object target = origData;
        StringBuilder debugSb = new StringBuilder();
        for (int i = 0; i < paramPathArray.length; i++) {
            String paramName = paramPathArray[i];
            if(StringUtils.isEmpty(paramName)){
                throw new ProcessFailedException(MessageFormat.format("要提取值的目标变量名为空，无法执行map.get。当前已循环：{1}",paramName, debugSb.toString()).toString());
            }
            if(!(target instanceof Map)){
                throw new ProcessFailedException(MessageFormat.format("要提取值的目标对象非Map。无法执行提取\"{0}\"变量。当前已循环：{1}",paramName, debugSb.toString()).toString());
            }
            target = ((Map)target).get(paramName);
            debugSb.append("/").append(paramName);
            // 如果获取的值为空，且又不是最后一个（那表示已经无法继续循环获取下去）
            if(target==null && i<(paramPathArray.length-1)){
                throw new ProcessFailedException(MessageFormat.format("从map中提取数据失败！循环到变量\"{0}\"为空！无法继续往下循环。当前已循环：{1}",paramName,debugSb.toString()).toString());
            }

        }

        return target;
    }

//    public static void main(String[] args) throws IOException, ProcessFailedException {
//        /* -------------------------------------------------------------------------- test getData() -------------------------------------------------------------------------- */
//        String json = "{\"lv1\":{\"lv2\":\n" +
//                "{\"success\":true,\"data\":[{\"id\":549,\"schemaName\":\"【年终大促】3件7折\",\"schemaCode\":\"171129155941895\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“摩登年终大促”活动“多件多折”专场商品使用\",\"startTime\":\"2017-11-29 15:58:00\",\"endTime\":\"2017-12-06 00:00:00\",\"favType\":2,\"favTypeName\":\"折扣\",\"favLine\":\"3件7折\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"949\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"https://m.ma-test.com/act_v2/458.html\",\"onlineStatus\":1,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":548,\"schemaName\":\"【年终大促】2件8折\",\"schemaCode\":\"171129155750936\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“摩登年终大促”活动“多件多折”专场商品使用\",\"startTime\":\"2017-11-29 15:56:00\",\"endTime\":\"2017-12-06 00:00:00\",\"favType\":2,\"favTypeName\":\"折扣\",\"favLine\":\"2件8折\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"949\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"https://m.ma-test.com/act_v2/458.html\",\"onlineStatus\":1,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":544,\"schemaName\":\"111111\",\"schemaCode\":\"171128162627965\",\"schemaType\":2,\"status\":1,\"actMsg\":\"\",\"startTime\":\"2017-11-28 16:29:00\",\"endTime\":\"2017-11-30 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"111111111\",\"favLineUnit\":1,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"\",\"platforms\":[\"2\"]},\"perLimitNum\":1,\"stockNum\":1111,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"移动\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":536,\"schemaName\":\"3件7折\",\"schemaCode\":\"171122153648648\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“淘空保税仓”活动“多件多折专场”商品使用\",\"startTime\":\"2017-11-22 15:36:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":2,\"favTypeName\":\"折扣\",\"favLine\":\"3件7折\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"949\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":535,\"schemaName\":\"2件8折\",\"schemaCode\":\"171122153553250\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“淘空保税仓”活动“多件多折专场”商品使用\",\"startTime\":\"2017-11-22 15:34:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":2,\"favTypeName\":\"折扣\",\"favLine\":\"2件8折\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"949\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":534,\"schemaName\":\"150元2件\",\"schemaCode\":\"171122153054053\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“淘空保税仓”活动“150元2件专场”商品使用\",\"startTime\":\"2017-11-22 15:29:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"150元2件\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"948\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":533,\"schemaName\":\"【年终大促】3件100元\",\"schemaCode\":\"171122152346041\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“摩登年终大促”活动“100元3件专场”商品使用\",\"startTime\":\"2017-11-22 15:21:00\",\"endTime\":\"2017-11-30 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"3件100元\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"947\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":532,\"schemaName\":\"100元10件\",\"schemaCode\":\"171122152053661\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“淘空保税仓”活动“护齿专场”商品使用\",\"startTime\":\"2017-11-22 15:09:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"100元5件\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"946\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":531,\"schemaName\":\"50元4件\",\"schemaCode\":\"171122150719123\",\"schemaType\":2,\"status\":1,\"actMsg\":\"限“淘空保税仓”活动“护齿专场”商品使用\",\"startTime\":\"2017-11-22 15:05:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"50元4件\",\"favLineUnit\":3,\"schemaScopes\":{\"taxTypes\":[\"0\"],\"stageId\":\"946\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"保税\"},{\"id\":530,\"schemaName\":\"20元测试券\",\"schemaCode\":\"171121114043987\",\"schemaType\":2,\"status\":1,\"actMsg\":\"20元测试券\",\"startTime\":\"2017-11-21 11:40:00\",\"endTime\":\"2017-11-23 00:00:00\",\"favType\":1,\"favTypeName\":\"满减\",\"favLine\":\"20\",\"favLineUnit\":1,\"schemaScopes\":{\"taxTypes\":[\"-1\"],\"stageId\":\"\",\"platforms\":[\"-1\"]},\"perLimitNum\":-1,\"stockNum\":-1,\"targetUrl\":\"\",\"onlineStatus\":2,\"scopePlatformRemark\":\"全站通用\",\"scopeTaxTypeRemark\":\"特殊商品\"}],\"extData\":{},\"total\":496}\n" +
//                "}}";
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map jsonMap = objectMapper.readValue(json, Map.class);
//        Object result = getData(jsonMap, new String[]{"lv1","lv2","data"});
//        System.out.println("done!");
//    }
}
