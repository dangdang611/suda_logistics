package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.InformationJobDO;
import com.example.system.entity.InformationNewsDO;
import com.example.system.entity.LogisticsUserAddressDO;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.exception.OperationException;
import com.example.system.mapper.InformationNewsMapper;
import com.example.system.service.InformationNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新闻资讯表 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class InformationNewsServiceImpl extends ServiceImpl<InformationNewsMapper, InformationNewsDO> implements InformationNewsService {

    @Autowired
    private InformationNewsMapper mapper;

    @Override
    public int insertInformationNews(InformationNewsDO informationNews) {
        return mapper.insert(informationNews);
    }

    @Override
    public int updateInformationNews(InformationNewsDO informationNews) {
        return mapper.updateById(informationNews);
    }

    @Override
    public List<InformationNewsDO> selectInformationNewsItemsDescField(Integer page, Integer size , String field) {
        QueryWrapper<InformationNewsDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(field);//降序输出

        Page<InformationNewsDO> pageItem = new Page<>(page,size);
        mapper.selectPage(pageItem,queryWrapper);
        return pageItem.getRecords();
    }

    @Override
    public List<InformationNewsDO> selectInformationNewsLikeNewsTitle(String newsTitle) {
        QueryWrapper<InformationNewsDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("news_title",newsTitle);

        return mapper.selectList(queryWrapper);
    }

    @Override
    public InformationNewsDO selectUpById(Long newsId) {

        List<Long> list = mapper.selectListId();
        if (list.get(0).equals(newsId))return null;//当前id就是第一个，无上一页

        Long ansId = 0L;
        int i;
        for(i=0; i<list.size() ; i++)
        {
            if(newsId.equals(list.get(i)))break;
        }
        if (i == list.size())throw new OperationException("传入id值错误");
        return mapper.selectById(list.get(i-1));
    }

    @Override
    public InformationNewsDO selectDownById(Long newsId) {
        List<Long> list = mapper.selectListId();
        if (list.get(list.size()-1).equals(newsId))return null;//当前id就是最后一个，无下一页

        Long ansId = 0L;
        int i;
        for(i=0; i<list.size() ; i++)
        {
            if(newsId.equals(list.get(i)))break;
        }
        if (i == list.size())throw new OperationException("传入id值错误");
        return mapper.selectById(list.get(i+1));
    }
}
