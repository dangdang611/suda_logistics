package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.mapper.InformationJobMapper;
import com.example.system.entity.InformationJobDO;
import com.example.system.service.InformationJobService;
import com.example.system.vo.unify.MyGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 招聘资讯表 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class InformationJobServiceImpl extends ServiceImpl<InformationJobMapper, InformationJobDO> implements InformationJobService {

    @Autowired
    private InformationJobMapper mapper;

    @Override
    public int insertInformationJob(InformationJobDO informationJob) {
        return mapper.insert(informationJob);
    }

    @Override
    public int updateInformationJob(InformationJobDO informationJob) {
        return mapper.updateById(informationJob);
    }

    @Override
    public List<InformationJobDO> selectInformationJobItems(Integer page, Integer size) {
        QueryWrapper<InformationJobDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("job_id");//降序输出

        Page<InformationJobDO> pageItem = new Page<>(page,size);
        mapper.selectPage(pageItem,queryWrapper);
        return pageItem.getRecords();
    }

    @Override
    public List<InformationJobDO> selectInformationJobDOLikeJobName(String JobName) {
        QueryWrapper<InformationJobDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("job_name",JobName);

        return mapper.selectList(queryWrapper);
    }

    @Override
    public MyGeneral<InformationJobDO> selectInformationJobDOLikeFieldDescTime(List<String> fieldList, List<String> contextList,
                                                                               Integer page , Integer size) {
        //为传入需要搜索的键值就查询所有
        QueryWrapper<InformationJobDO> queryWrapper = new QueryWrapper<>();

        if(fieldList != null && fieldList.size() != 0 && contextList != null && contextList.size() != 0){

            for (int i = 0 ; i < fieldList.size() ; i ++){
                if (contextList.get(i) != null)
                    queryWrapper.like(fieldList.get(i), contextList.get(i));//设置搜索关键字
            }

        }
        queryWrapper.orderByDesc("insert_time");//降序输出
        Page<InformationJobDO> informationJobDOPage = new Page<>(page, size);
        mapper.selectPage(informationJobDOPage, queryWrapper);//分页查询

        return new MyGeneral<InformationJobDO>().data(informationJobDOPage.getRecords()).count(informationJobDOPage.getTotal());
    }
}
