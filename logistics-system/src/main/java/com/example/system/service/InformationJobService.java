package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.InformationJobDO;
import com.example.system.vo.unify.MyGeneral;

import java.util.List;

/**
 * <p>
 * 招聘资讯表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface InformationJobService extends IService<InformationJobDO> {

    int insertInformationJob(InformationJobDO informationJob);

    int updateInformationJob(InformationJobDO informationJob);

    List<InformationJobDO> selectInformationJobItems(Integer page, Integer size);

    List<InformationJobDO> selectInformationJobDOLikeJobName(String JobName);

    MyGeneral<InformationJobDO> selectInformationJobDOLikeFieldDescTime(List<String> fieldList, List<String> contextList, Integer page , Integer size);

}
