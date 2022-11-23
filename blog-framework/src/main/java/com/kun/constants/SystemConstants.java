package com.kun.constants;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 22:00
 */
public class SystemConstants {

    /**
     *  文章是草稿
     */
    public static final Integer ARTICLE_STATUS_DRAFT = 1;

    /**
     *  文章是正常分布状态
     */
    public static final Integer ARTICLE_STATUS_NORMAL = 0;

    /**
     * 分页查询文章当前页
     */
    public static final int ARTICLE_PAGE_CUR = 1;
    /**
     * 分页查询文章每页数量
     */
    public static final int ARTICLE_PAGE_SIZE = 10;

    /**
     * 禁用状态
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 正常状态
     */
    public static final String STATUS_DISABLE = "1";

    /**
     * Link的审核状态正常
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 前端请求头token的key
     */
    public static final String HEADER_TOKEN = "token";

    /**
     * 根评论
     */
    public static final Long ROOT_ID = -1L;

    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 菜单类型 F
     */
    public static final String MENU_TYPE_F = "F";
    /**
     * 菜单类型 M
     */
    public static final String MENU_TYPE_M = "M";
    /**
     * 菜单类型 C
     */
    public static final String MENU_TYPE_C = "C";

    public static final String ADMIN_TYPE = "1";

    public static final String NORMAL = "0";
}
