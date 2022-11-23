package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.Category;
import com.kun.domain.vo.PageVO;
import com.kun.service.ICategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kun
 * @since 2022-11-22 21:53
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @GetMapping("/listAllCategory")
    public Result listAllCategory() {
        return categoryService.listAllCategory();
    }

    @GetMapping("/export")
    @PreAuthorize("@os.hasPermission('content:category:export')")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }

    @GetMapping("/list")
    public Result list(Category category, Integer pageNum, Integer pageSize) {
        PageVO<Category> pageVo = categoryService.selectCategoryPage(category, pageNum, pageSize);
        return Result.okResult(pageVo);
    }

    @PutMapping
    public Result edit(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.okResult();
    }

    @DeleteMapping(value = "/{id}")
    public Result remove(@PathVariable(value = "id") Long id) {
        categoryService.removeById(id);
        return Result.okResult();
    }

    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getById(id);
        return Result.okResult(category);
    }

    @PostMapping
    public Result add(@RequestBody Category category){
        categoryService.save(category);
        return Result.okResult();
    }
}
