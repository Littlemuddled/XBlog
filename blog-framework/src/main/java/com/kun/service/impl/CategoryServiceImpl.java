package com.kun.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Article;
import com.kun.domain.entity.Category;
import com.kun.domain.vo.CategoryAdminVO;
import com.kun.domain.vo.CategoryVO;
import com.kun.domain.vo.ExcelCategoryVO;
import com.kun.domain.vo.PageVO;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.mapper.CategoryMapper;
import com.kun.service.IArticleService;
import com.kun.service.ICategoryService;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private IArticleService articleService;

    @Override
    public Result getCategoryList() {
        //查询已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);

        //获取文章的分类id，且去重
        Set<Long> articleIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //查询文章分类表
        List<Category> categoryList = this.listByIds(articleIds);
        List<Category> categories = categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装VO类
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);

        return Result.okResult(categoryVOS);
    }

    @Override
    public Result listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categoryList = this.list(queryWrapper);
        List<CategoryAdminVO> categoryAdminVOS = BeanCopyUtils.copyBeanList(categoryList, CategoryAdminVO.class);
        return Result.okResult(categoryAdminVOS);
    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryList = this.list();
            List<ExcelCategoryVO> excelCategoryVOS = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVO.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVOS);
        } catch (IOException e) {
            Result result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(response));
        }
    }

    @Override
    public PageVO<Category> selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());

        Page<Category> page = new Page<>(pageNum,pageSize);
        this.page(page,queryWrapper);

        //转换成VO
        List<Category> categories = page.getRecords();

        PageVO<Category> pageVo = new PageVO<>(categories,page.getTotal());
        return pageVo;
    }
}
