package com.kun.controller;

import com.kun.domain.Result;
import com.kun.service.ILinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private ILinkService linkService;

    @GetMapping("/getAllLink")
    public Result getAllLink() {
        return linkService.getAllLink();
    }

}
