package cn.acyou.gemini.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 04:42]
 **/
public interface ReptileMapper {

    void createSecuritiesTimesHSATable(@Param("suffix") String suffix);

    /**
     * 检查表名是否存在
     * @param tableName 表名称
     * @return 表名列表
     */
    List<String> checkTableExists(@Param("tableName") String tableName);

    /**
     * 获取表中的总数据量
     * @param tableName 表名称
     * @return totalCount
     */
    Long getTableTotalCount(@Param("tableName") String tableName);

}
