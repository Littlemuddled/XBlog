package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.Link;
import com.kun.domain.vo.PageVO;
import com.kun.service.ILinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author kun
 * @since 2022-11-23 22:24
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {


    @Resource
    private ILinkService linkService;

    @GetMapping("/list")
    public Result list(Link link, Integer pageNum, Integer pageSize) {
        PageVO<Link> pageVo = linkService.selectLinkPage(link, pageNum, pageSize);
        return Result.okResult(pageVo);
    }

    @PostMapping
    public Result add(@RequestBody Link link) {
        linkService.save(link);
        return Result.okResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        linkService.removeById(id);
        return Result.okResult();
    }

    @PutMapping
    public Result edit(@RequestBody Link link) {
        linkService.updateById(link);
        return Result.okResult();
    }

    @PutMapping("/changeLinkStatus")
    public Result changeLinkStatus(@RequestBody Link link) {
        linkService.updateById(link);
        return Result.okResult();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable(value = "id") Long id) {
        Link link = linkService.getById(id);
        return Result.okResult(link);
    }
}
