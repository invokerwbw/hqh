package com.dzwww.hqh.controller;

import com.dzwww.hqh.entity.PageData;
import com.dzwww.hqh.entity.Pager;
import com.dzwww.hqh.service.HqhService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层
 */
@Controller
public class HqhController {

    @Resource
    private HqhService hqhService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {

        List<Map<String, String>> gr = hqhService.listXm("1", 1, 10).getRows();//个人项目
        List<Map<String, String>> dw = hqhService.listXm("2", 1, 10).getRows();//单位项目
        List<Map<String, String>> hw = hqhService.listXm("5", 1, 10).getRows();//海外留学人员项目
        List<Map<String, String>> gn = hqhService.listXm("6", 1, 10).getRows();//国内博士后项目

        model.addAttribute("gr", gr);
        model.addAttribute("dw", dw);
        model.addAttribute("hw", hw);
        model.addAttribute("gn", gn);

        return "index";
    }

    /**
     * 根据分类获取项目列表
     *
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/xm", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Map<String, String>> listXm(@RequestParam String type, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return hqhService.listXm(type, pageNum, pageSize).getRows();
    }

    /**
     * 详情页
     *
     * @param type
     * @param key
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detail(@RequestParam String type, @RequestParam String key, Model model) {

        Map<String, String> detail = hqhService.xmDetail(type, key);

        model.addAttribute("detail", detail);

        return "content";
    }

    /**
     * 列表页
     *
     * @param type
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/more")
    public String more(@RequestParam String type, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, Model model) {

        PageData pageData = hqhService.listXm(type, pageNum, pageSize);

        //分页
        List<Map<String, String>> page = new ArrayList<>();
        String title = "";
        Pager pager = pageData.getPager();
        if (pager != null) {
            int no = pager.getPageno();//当前页数
            int size = pager.getPagesize();//每页条数
            int records = pager.getRecords();//总条数
            int min = 1;//最小页数
            int max = (int) Math.ceil(records / size);//最大页数

            Map<String, String> shyy = new HashMap<>();//上一页
            shyy.put("name", "上一页");
            shyy.put("class", "");
            shyy.put("href", "/more?type=" + type + "&pageNum=" + (no - 1));
            Map<String, String> shy = new HashMap<>();//首页
            shy.put("name", "«");
            shy.put("class", "");
            shy.put("href", "/more?type=" + type + "&pageNum=" + min);
            Map<String, String> wy = new HashMap<>();//尾页
            wy.put("name", "»");
            wy.put("class", "");
            wy.put("href", "/more?type=" + type + "&pageNum=" + max);
            Map<String, String> xyy = new HashMap<>();//下一页
            xyy.put("name", "下一页");
            xyy.put("class", "");
            xyy.put("href", "/more?type=" + type + "&pageNum=" + (no + 1));

            //当前页为首页时的处理
            if (no <= min) {
                no = min;
                shyy.put("class", "disabled");
                shyy.put("href", "/more?type=" + type + "&pageNum=" + min);
                shy.put("class", "disabled");
            }

            //当前页为尾页时的处理
            if (no >= max) {
                no = max;
                wy.put("class", "disabled");
                xyy.put("class", "disabled");
                xyy.put("href", "/more?type=" + type + "&pageNum=" + max);
            }

            page.add(shyy);
            page.add(shy);

            //当页数大于5页时进行处理
            int minD = 1;
            int maxD = no;
            if (max >= 5) {
                minD = no - 2;
                maxD = no + 2;
                if (minD < min) {
                    maxD = maxD + (min - minD);
                    minD = min;
                } else if (maxD > max) {
                    minD = minD - (maxD - max);
                    maxD = max;
                }
            }

            Map<String, String> p;
            for (int i = minD; i <= maxD; i++) {
                p = new HashMap<>();
                p.put("name", String.valueOf(i));
                p.put("href", "/more?type=" + type + "&pageNum=" + i);
                if (i == no) {
                    p.put("class", "active");
                } else {
                    p.put("class", "");
                }
                page.add(p);
            }

            page.add(wy);
            page.add(xyy);

            if ("1".equals(type)) {
                title = "个人项目";
                type = "3";
            }
            if ("2".equals(type)) {
                title = "单位项目";
                type = "4";
            }
        }

        model.addAttribute("type", type);
        model.addAttribute("title", title);
        model.addAttribute("page", page);
        model.addAttribute("rows", pageData.getRows() != null ? pageData.getRows() : new ArrayList<>());

        return "list";
    }

}
