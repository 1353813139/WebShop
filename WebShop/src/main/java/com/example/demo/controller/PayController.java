package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.domain.Consumption;
import com.example.demo.domain.Good;
import com.example.demo.repository.CartRespository;
import com.example.demo.repository.ConsumptionRespository;
import com.example.demo.repository.GoodRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class PayController {

    @Autowired
    private CartRespository cartRespository;
    @Autowired
    private GoodRespository goodRespository;
    @Autowired
    private ConsumptionRespository consumptionRespository;

    @RequestMapping(value = "/cart",method = RequestMethod.GET)
    public String Cart(HttpServletRequest request,Model model){
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
        List<Cart> carts = cartRespository.findCartsByUsername(username);
        model.addAttribute("carts",carts);
        model.addAttribute("username",username);
        return "/cart";
    }
    @RequestMapping(value = "/addnum",method = RequestMethod.POST)
    @ResponseBody
    public void AddNum(@RequestParam(value = "name") String name,HttpServletRequest request){
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        Cart cart = cartRespository.findCartByUsernameAndName(username,name);
        int amout = cart.getAmout();
        cart.setAmout(amout + 1);
        cartRespository.save(cart);
    }
    @RequestMapping(value = "/reducenum",method = RequestMethod.POST)
    @ResponseBody
    public void ReduceNum(@RequestParam(value = "name") String name,HttpServletRequest request){
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        Cart cart = cartRespository.findCartByUsernameAndName(username,name);
        int amout = cart.getAmout();
        cart.setAmout(amout - 1);
        cartRespository.save(cart);
    }

    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public String Pay(HttpServletRequest request,Model model){
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
        List<Cart> carts = cartRespository.findCartsByUsername(username);
        Double total = 0.;
        for(int i = 0;i < carts.size();i++){
            total = total + carts.get(i).getTotal();
        }
        model.addAttribute("carts",carts);
        model.addAttribute("total",total);
        model.addAttribute("username",username);
        return "/pay";
    }

    @Transactional
    @RequestMapping(value = "/clear",method = RequestMethod.GET)
    public String Clear(HttpServletRequest request){
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
        cartRespository.deleteByUsername(username);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/addcart",method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Boolean> addCart(@RequestParam(value = "name") String name, HttpServletRequest request){
        Object user = request.getSession().getAttribute("username");
        String username = user.toString();
        System.out.println(name);
        Good good = goodRespository.findGoodByName(name);
        HashMap<String,Boolean> result = new HashMap<String,Boolean>();
        List<Cart> carts = cartRespository.findCartsByUsernameAndName(username,name);
        if(carts.size() == 0){
            Double price = good.getPrice();
            String image= good.getImage();
            Cart cart = new Cart(username,name,price,image);
            cartRespository.save(cart);
            result.put("type",true);
        }
        else {
            result.put("type",false);
        }
        return result;
    }

    @Transactional
    @RequestMapping(value = "/placeorder",method = RequestMethod.GET)
    public String placeOrder(HttpServletRequest request){
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
        List<Cart> carts = cartRespository.findCartsByUsername(username);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String  date = dateFormat.format(new Date());
        for (int i = 0;i < carts.size();i++){
            Consumption consumption = new Consumption(username,carts.get(i).getName(),carts.get(i).getAmout(),carts.get(i).getPrice(),date);
            consumptionRespository.save(consumption);
        }
        cartRespository.deleteByUsername(username);
        return "redirect:/cart";
    }
}
