package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.dto.TagDto;
import com.kun.domain.entity.Tag;
import com.kun.service.ITagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    private ITagService tagService;

    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, TagDto tagDto) {
        return tagService.pageTagList(pageNum,pageSize,tagDto);
    }

    @PostMapping
    public Result addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public Result deleteTag(@PathVariable Long id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public Result getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    public Result updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @GetMapping("/listAllTag")
    public Result listAllTag() {
        return tagService.listAllTag();
    }
}
