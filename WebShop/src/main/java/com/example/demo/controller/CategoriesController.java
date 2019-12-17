package com.example.demo.controller;

import com.example.demo.domain.Good;
import com.example.demo.domain.History;
import com.example.demo.repository.GoodRespository;
import com.example.demo.repository.HistoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;


@Controller
public class CategoriesController {

    @Autowired
    private GoodRespository goodRespository;
    @Autowired
    private HistoryRespository historyRespository;

    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    public String Categories(HttpServletRequest request, Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin != null){
            return "redirect:/noright";
        }
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        List<Good> goods = goodRespository.findAll();
        List<Good> hots = goodRespository.findGoodsByTagEquals("hot");
        List<Good> news = goodRespository.findGoodsByTagEquals("new");
        List<Good> sales = goodRespository.findGoodsByTagEquals("sale");
        model.addAttribute("goods",goods);
        model.addAttribute("hots",hots);
        model.addAttribute("news",news);
        model.addAttribute("sales",sales);
        model.addAttribute("username",username);
        return "/categories";
    }

    @RequestMapping(value = "/product",method = RequestMethod.GET)
    public String Product(int id, HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin != null){
            return "redirect:/noright";
        }
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String  date = dateFormat.format(new Date());
        Good good = goodRespository.findById(id).get();
        String name = good.getName();
        History history = new History(username,name,date);
        historyRespository.save(history);
        model.addAttribute("good",good);
        model.addAttribute("username",username);
        return "/product";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String Search(String condition,HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin != null){
            return "redirect:/noright";
        }
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        List<Good> goods = goodRespository.findGoodsByNameLike("%"+condition+"%");
        ArrayList<Good> hots = new ArrayList<Good>();
        ArrayList<Good> news = new ArrayList<Good>();
        ArrayList<Good> sales = new ArrayList<Good>();
        for(int i = 0;i < goods.size();i++){
            if(goods.get(i).getTag().equals("hot")){
                hots.add(goods.get(i));
            }
            if(goods.get(i).getTag().equals("new")){
                news.add(goods.get(i));
            }
            if(goods.get(i).getTag().equals("sale")){
                sales.add(goods.get(i));
            }
        }
        model.addAttribute("goods",goods);
        model.addAttribute("hots",hots);
        model.addAttribute("news",news);
        model.addAttribute("sales",sales);
        model.addAttribute("username",username);
        return "/categories";
    }
}
