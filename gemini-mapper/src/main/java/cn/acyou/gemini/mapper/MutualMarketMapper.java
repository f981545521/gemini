package cn.acyou.gemini.mapper;

import cn.acyou.gemini.entity.MutualMarket;
import cn.acyou.gemini.entity.SecuritiesTimesHuShenA;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 07:23]
 **/
public interface MutualMarketMapper extends BaseMapper<MutualMarket> {

    void insertBatch(@Param("tableName") String tableName, @Param("hushenAList") List<MutualMarket> hushenAList);

}
