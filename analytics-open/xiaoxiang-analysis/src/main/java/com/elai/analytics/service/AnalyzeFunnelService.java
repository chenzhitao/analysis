package com.elai.analytics.service;

import java.util.List;
import com.elai.analytics.domain.AnalyzeFunnelDO;

/**
 * 分析漏斗Service接口
 *
 * @author elai
 * @date 2021-05-01
 */
public interface AnalyzeFunnelService
{
    /**
     * 查询分析漏斗
     *
     * @param id 分析漏斗ID
     * @return 分析漏斗
     */
    AnalyzeFunnelDO selectAnalyzeFunnelById(Long id);

    /**
     * 查询分析漏斗列表
     *
     * @param analyzeFunnel 分析漏斗
     * @return 分析漏斗集合
     */
    List<AnalyzeFunnelDO> selectAnalyzeFunnelList(AnalyzeFunnelDO analyzeFunnel);

    /**
     * 新增分析漏斗
     *
     * @param analyzeFunnel 分析漏斗
     * @return 结果
     */
    int insertAnalyzeFunnel(AnalyzeFunnelDO analyzeFunnel);

    /**
     * 修改分析漏斗
     *
     * @param analyzeFunnel 分析漏斗
     * @return 结果
     */
    int updateAnalyzeFunnel(AnalyzeFunnelDO analyzeFunnel);

    /**
     * 批量删除分析漏斗
     *
     * @param ids 需要删除的分析漏斗ID
     * @return 结果
     */
    int deleteAnalyzeFunnelByIds(Long[] ids);

    /**
     * 删除分析漏斗信息
     *
     * @param id 分析漏斗ID
     * @return 结果
     */
    int deleteAnalyzeFunnelById(Long id);
}
