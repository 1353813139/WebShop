package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BackController {
    @Autowired
    private ConsumptionRespository consumptionRespository;
    @Autowired
    private GoodRespository goodRespository;
    @Autowired
    private HistoryRespository historyRespository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private SendEmail sendEmail;

    @RequestMapping(value = "/backreport",method = RequestMethod.GET)
    public String getBackreport(HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin == null){
            return "redirect:/noright";
        }
        Object user = request.getSession().getAttribute("username");
        String adname = user.toString();
        List<Good> goods = goodRespository.findAll();
        ArrayList<Report> reports = new ArrayList<Report>();
        List<Consumption> crecords = consumptionRespository.findAll();
        for(int i = 0;i < goods.size();i++){
            String name = goods.get(i).getName();
            List<Consumption> consumptions = consumptionRespository.findConsumptionsByName(name);
            int temp1 = 0;
            Double temp2 = 0.;
            for(int j = 0;j < consumptions.size();j++){
                temp1 = temp1 + consumptions.get(j).getAmout();
                temp2 = temp2 + consumptions.get(j).getTotal();
            }
            Report report = new Report(name,goods.get(i).getPrice(),temp1,temp2,goods.get(i).getNum());
            reports.add(report);
        }
        model.addAttribute("reports",reports);
        model.addAttribute("crecords",crecords);
        model.addAttribute("adname",adname);
        return "/backreport";
    }
    @RequestMapping(value = "/deliver",method = RequestMethod.POST)
    @ResponseBody
    public void Deliver(@RequestParam(value = "id") int id){
        Consumption consumption = consumptionRespository.findById(id).get();
        consumption.setState("已发货");
        String name = consumption.getName();
        int sellnum = consumption.getAmout();
        String username = consumption.getUsername();
        Good good = goodRespository.findGoodByName(name);
        if(good == null){
            System.out.println("某啦");
        }
        else {
            int num = good.getNum();
            if(num <= sellnum){
                good.setNum(0);
                goodRespository.save(good);
            }
            else {
                good.setNum(num - sellnum);
                goodRespository.save(good);
            }
        }
        consumptionRespository.save(consumption);
        User user = userRespository.findUserByUsername(username);
        String email = user.getE_mail();
        String address = user.getAddress();
        String content = "尊敬的用户:" + username + "\n"
                         +"您购买的商品：  " + name + "  已发货\n"+
                        "目的地：  " + address + "\n" + "商品已发货，请耐心等待。";
        try{
            sendEmail.sendMsg(email,content);
        }
        catch (Exception e){

        };
    }

    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public String getHistory(HttpServletRequest request,Model model){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin == null){
            return "redirect:/noright";
        }
        Object user = request.getSession().getAttribute("username");
        String adname = user.toString();
        List<User> users = userRespository.findAll();
        ArrayList<List<History>> his = new ArrayList<List<History>>();
        ArrayList<List<Consumption>> cons = new ArrayList<List<Consumption>>();
        for(int i = 0;i < users.size();i++){
            String username = users.get(i).getUsername();
            List<History> histories = historyRespository.findHistoriesByUsername(username);
            List<Consumption> consumptions = consumptionRespository.findConsumptionsByUsername(username);
            if(histories.size() != 0){
                his.add(histories);
            }
            if(consumptions.size() != 0){
                cons.add(consumptions);
            }
        }
        model.addAttribute("adname",adname);
        model.addAttribute("cons",cons);
        model.addAttribute("his",his);
        return "/history";
    }
}
