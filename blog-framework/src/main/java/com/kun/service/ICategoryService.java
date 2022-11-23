package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.entity.Category;
import com.kun.domain.vo.PageVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
public interface ICategoryService extends IService<Category> {

    Result getCategoryList();

    Result listAllCategory();

    void export(HttpServletResponse response);

    PageVO<Category> selectCategoryPage(Category category, Integer pageNum, Integer pageSize);

}
