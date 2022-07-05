package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.InformationNewsDO;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsUserDO;

import java.util.List;

/**
 * <p>
 * 新闻资讯表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface InformationNewsService extends IService<InformationNewsDO> {

    int insertInformationNews(InformationNewsDO informationNews);

    int updateInformationNews(InformationNewsDO informationNews);

    List<InformationNewsDO> selectInformationNewsItemsDescField(Integer page, Integer size , String field);

    List<InformationNewsDO> selectInformationNewsLikeNewsTitle(String newsTitle);

    InformationNewsDO selectUpById(Long newsId);

    InformationNewsDO selectDownById(Long newsId);
}
