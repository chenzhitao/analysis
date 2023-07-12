package com.elai.analytics.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elai.analytics.mapper.AnalyzeFunnelMapper;
import com.elai.analytics.domain.AnalyzeFunnelDO;
import com.elai.analytics.service.AnalyzeFunnelService;

/**
 * 分析漏斗Service业务层处理
 *
 * @author elai
 * @date 2021-05-01
 */
@Service
public class AnalyzeFunnelServiceImpl implements AnalyzeFunnelService
{
    @Autowired
    private AnalyzeFunnelMapper analyzeFunnelMapper;

    /**
     * 查询分析漏斗
     *
     * @param id 分析漏斗ID
     * @return 分析漏斗
     */
    @Override
    public AnalyzeFunnelDO selectAnalyzeFunnelById(Long id)
    {
        return analyzeFunnelMapper.selectAnalyzeFunnelById(id);
    }

    /**
     * 查询分析漏斗列表
     *
     * @param analyzeFunnel 分析漏斗
     * @return 分析漏斗
     */
    @Override
    public List<AnalyzeFunnelDO> selectAnalyzeFunnelList(AnalyzeFunnelDO analyzeFunnel)
    {
        return analyzeFunnelMapper.selectAnalyzeFunnelList(analyzeFunnel);
    }

    /**
     * 新增分析漏斗
     *
     * @param analyzeFunnel 分析漏斗
     * @return 结果
     */
    @Override
    public int insertAnalyzeFunnel(AnalyzeFunnelDO analyzeFunnel)
    {
        return analyzeFunnelMapper.insertAnalyzeFunnel(analyzeFunnel);
    }

    /**
     * 修改分析漏斗
     *
     * @param analyzeFunnel 分析漏斗
     * @return 结果
     */
    @Override
    public int updateAnalyzeFunnel(AnalyzeFunnelDO analyzeFunnel)
    {
        return analyzeFunnelMapper.updateAnalyzeFunnel(analyzeFunnel);
    }

    /**
     * 批量删除分析漏斗
     *
     * @param ids 需要删除的分析漏斗ID
     * @return 结果
     */
    @Override
    public int deleteAnalyzeFunnelByIds(Long[] ids)
    {
        return analyzeFunnelMapper.deleteAnalyzeFunnelByIds(ids);
    }

    /**
     * 删除分析漏斗信息
     *
     * @param id 分析漏斗ID
     * @return 结果
     */
    @Override
    public int deleteAnalyzeFunnelById(Long id)
    {
        return analyzeFunnelMapper.deleteAnalyzeFunnelById(id);
    }
}
