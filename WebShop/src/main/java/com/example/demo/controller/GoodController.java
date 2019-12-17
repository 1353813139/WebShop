package com.example.demo.controller;

import com.example.demo.domain.Good;
import com.example.demo.repository.GoodRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.List;

@Controller
public class GoodController {
    @Autowired
    private GoodRespository goodRespository;

    @RequestMapping(value = "/checkout",method = RequestMethod.GET)
    public String getCategories(HttpServletRequest request,Model model){
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
        model.addAttribute("goods",goods);
        model.addAttribute("adname",adname);
        return "/checkout";
    }

    @RequestMapping(value = "/addrecord",method = RequestMethod.POST)
    public String addRecord(String name,Double price,String tag,Double rrp,int num,String introduction,HttpServletRequest request){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin == null){
            return "redirect:/noright";
        }
        String img = "images/product_18.jpg";
        if(tag.equals("normal")){
            tag = "";
        }
        Good good = new Good(name,price,tag,rrp,num,introduction,img);
        goodRespository.save(good);
        return "redirect:/checkout";
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String Delete(int id,HttpServletRequest request){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin == null){
            return "redirect:/noright";
        }
        Good good = goodRespository.findById(id).get();
        goodRespository.delete(good);
        return "redirect:/checkout";
    }
    @RequestMapping(value = "/getalert",method = RequestMethod.GET)
    public String getAlert(int id,Model model,HttpServletRequest request){
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
        Good good = goodRespository.findById(id).get();
        model.addAttribute(good);
        model.addAttribute("adname",adname);
        return "/checkout2";
    }
    @RequestMapping(value = "/alert",method = RequestMethod.POST)
    public String alert(int id,String name,Double price,String tag,Double rrp,int num,String introduction,HttpServletRequest request){
        Object is_login = request.getSession().getAttribute("is_login");
        if(is_login == null){
            return "redirect:/login";
        }
        Object is_admin = request.getSession().getAttribute("is_admin");
        if(is_admin == null){
            return "redirect:/noright";
        }
        Good good = goodRespository.findById(id).get();
        good.setName(name);
        good.setPrice(price);
        good.setTag(tag);
        good.setRrp(rrp);
        good.setNum(num);
        good.setIntroduction(introduction);
        goodRespository.save(good);
        return "redirect:/checkout";
    }
//    private void addRecord(){
//        Good good = new Good("2018秋季新款女式短款牛仔外套夹克",32.20,"sale",64.40,99,"尺码: S M L\n" +
//                "风格: 街头\n" +
//                "颜色分类: 牛仔外套\n" +
//                "年份季节: 2018年秋季\n" +
//                "衣长: 短款\n" +
//                "流行元素/工艺: 纽扣","images/product_3.jpg");
//        goodRespository.save(good);
//        good = new Good("2019新款夏天手工编织包",59.90,"nomal",99,"品牌: other/其他\n" +
//                "质地: 木制\n" +
//                "闭合方式: 锁扣\n" +
//                "图案: 纯色\n" +
//                "风格: 商务/OL\n" +
//                "形状: 圆盒形\n" +
//                "成色: 全新\n" +
//                "流行元素: 编织\n" +
//                "颜色分类: 深棕色\n" +
//                "有无夹层: 无\n" +
//                "是否可折叠: 否\n" +
//                "货号: 棕色藤圆\n" +
//                "背包方式: 单肩\n" +
//                "适用场景: 休闲\n" +
//                "里料材质: 涤纶\n" +
//                "肩带样式: 单根\n" +
//                "适用对象: 青年\n" +
//                "提拎部件类型: 软把\n" +
//                "箱包外袋种类: 敞口袋\n" +
//                "箱包硬度: 硬\n" +
//                "款式: 单肩包\n" +
//                "大小: 小\n" +
//                "流行款式名称: 其他","images/product_4.jpg");
//        goodRespository.save(good);
//        good = new Good("正品 Common Projects CP 低帮小白鞋",79.90,"nomal",99,"品牌: Common Porejcs\n" +
//                "尺码: 35 36 37 38 39 40 41 42 43 44\n" +
//                "后跟高: 平跟(小于等于1cm)\n" +
//                "颜色分类: 全白 按皮鞋码购买 其他颜色请备注 黑白 浅蓝 橙色反毛 高级灰反毛 少量现货 部分在途\n" +
//                "上市年份季节: 2019年春季\n" +
//                "鞋头款式: 圆头\n" +
//                "跟底款式: 平底\n" +
//                "内里材质: 头层牛皮\n" +
//                "开口深度: 浅口\n" +
//                "款式: 板鞋\n" +
//                "帮面材质: 头层牛皮","images/product_5.jpg");
//        goodRespository.save(good);
//        good = new Good("LOEWE女士编织拼皮革手提包",59.90,"new",99,"品牌: LOEWE/罗意威\n" +
//                "质地: 草\n" +
//                "颜色分类: 不支持退换请确认好下单 默认简易包装\n" +
//                "款式: 手提包\n" +
//                "流行款式名称: 其他","images/product_6.jpg");
//        goodRespository.save(good);
//        good = new Good("Simwood简木男装",15.90,"nomal",99,"品牌名称：Simwood\n" +
//                "更多参数\n" +
//                "\n" +
//                "产品参数：\n" +
//                "品牌: Simwood\n" +
//                "牛仔面料: 常规牛仔布\n" +
//                "货号: 190032\n" +
//                "弹力: 无弹\n" +
//                "基础风格: 青春流行\n" +
//                "上市年份季节: 2019年春季\n" +
//                "厚薄: 常规\n" +
//                "材质成分: 棉100%","images/product_7.jpg");
//        goodRespository.save(good);
//        good = new Good("DIVIDED2019年新款 亮片网纱连衣裙",43.99,"sale",64.40,99,"品牌名称：H&M\n" +
//                "产品参数：\n" +
//                "品牌: H&M\n" +
//                "适用年龄: 25-29周岁\n" +
//                "尺码: 155/76A 160/80A 165/84A 170/96A 175/104A 165/88A 170/92A 175/100A\n" +
//                "颜色分类: 浅绿松石色\n" +
//                "货号: 0757978\n" +
//                "年份季节: 2019年夏季\n" +
//                "裙长: 中长裙\n" +
//                "销售渠道类型: 商场同款(线上线下都销售)\n" +
//                "材质成分: 聚酰胺纤维(锦纶)100%","images/product_8.jpg");
//        goodRespository.save(good);
//        good = new Good("耐克Nike18新款 Blazer",39.90,"hot",99,"品牌: Nike/耐克\n" +
//                "鞋码: 36 36.5 37.5 38 38.5 39\n" +
//                "颜色分类: BQ0033-111 BQ0033-100\n" +
//                "吊牌价: 599.0\n" +
//                "款号: BQ0033-111-100\n" +
//                "上市时间: 2018年秋季\n" +
//                "鞋帮高度: 低帮\n" +
//                "性别: 女子","images/product_9.jpg");
//        goodRespository.save(good);
//        good = new Good("Hollister2019年秋季新品修身牛仔裤",19.50,"nomal",99,"品牌名称：Hollister\n" +
//                "更多参数\n" +
//                "\n" +
//                "产品参数：\n" +
//                "品牌: Hollister\n" +
//                "货号: 300200-1\n" +
//                "基础风格: 青春流行\n" +
//                "上市年份季节: 2019年秋季\n" +
//                "销售渠道类型: 商场同款(线上线下都销售)\n" +
//                "材质成分: 其他100%","images/product_10.jpg");
//        goodRespository.save(good);
//        good = new Good("民族风流苏单肩包",32.20,"sale",64.40,99,"品牌: Hamermesh\n" +
//                "质地: 木制\n" +
//                "闭合方式: 磁扣\n" +
//                "图案: 纯色\n" +
//                "风格: 欧美时尚\n" +
//                "形状: 横款方形\n" +
//                "成色: 全新\n" +
//                "流行元素: 竹节\n" +
//                "颜色分类: 褐色 原色\n" +
//                "有无夹层: 无\n" +
//                "是否可折叠: 否\n" +
//                "货号: 3.27\n" +
//                "背包方式: 单肩斜挎\n" +
//                "适用场景: 休闲\n" +
//                "肩带样式: 单根\n" +
//                "适用对象: 青年\n" +
//                "提拎部件类型: 装卸式提把\n" +
//                "箱包硬度: 硬\n" +
//                "款式: 单肩包\n" +
//                "大小: 中\n" +
//                "流行款式名称: 小方包","images/product_11.jpg");
//        goodRespository.save(good);
//        good = new Good("design tshirts store graniph 1908新款T恤",59.90,"nomal",99,"品牌: graniph\n" +
//                "尺码: SS S M L\n" +
//                "领型: 圆领\n" +
//                "颜色: 蓝色+ little house 白色+ 火车（背面追火车的人） 白色+ katy 白色+ 生命的历史（满花） 白色+ 四季村庄（满花） 白色+ choo choo 火车 深绿色+ 动物与森林 白色+ 捉迷藏的熊 浅灰色+ 杂乱的字母 灰色+ 摇滚树懒\n" +
//                "基础风格: 时尚都市\n" +
//                "适用季节: 四季\n" +
//                "袖长: 短袖\n" +
//                "厚薄: 常规\n" +
//                "适用场景: 日常\n" +
//                "版型: 标准","images/product_12.jpg");
//        goodRespository.save(good);
//        good = new Good("Uterque 2019新款彩色结饰高跟凉鞋",39.90,"hot",99,"品牌名称：UTERQUE\n" +
//                "产品参数：\n" +
//                "品牌: UTERQUE\n" +
//                "尺码: 35 36 37 38 39 40 41\n" +
//                "后跟高: 超高跟(大于8cm)\n" +
//                "颜色分类: 黄色\n" +
//                "货号: 15022051202-25\n" +
//                "上市年份季节: 2019年秋季\n" +
//                "鞋头款式: 露趾\n" +
//                "跟底款式: 粗跟\n" +
//                "适用对象: 青年（18-40周岁）\n" +
//                "款式: 时装凉鞋\n" +
//                "帮面材质: 羊皮（除羊反绒/羊猄）","images/product_13.jpg");
//        goodRespository.save(good);
//        good = new Good("ZARA 新款 男装 绊带装饰衬衫",19.50,"new",99,"品牌名称：ZARA\n" +
//                "更多参数\n" +
//                "\n" +
//                "产品参数：\n" +
//                "品牌: ZARA\n" +
//                "货号: 07545401403-25\n" +
//                "基础风格: 时尚都市\n" +
//                "上市年份季节: 2019年冬季\n" +
//                "厚薄: 常规\n" +
//                "销售渠道类型: 商场同款(线上线下都销售)\n" +
//                "材质成分: 亚麻55% 棉45%","images/product_14.jpg");
//        goodRespository.save(good);
//        good = new Good("Red Valentino 彩虹帆布编织手柄手提包",32.20,"sale",64.40,99,"品牌: Valentino/华伦天奴\n" +
//                "质地: 帆布\n" +
//                "颜色分类: 彩色 不支持退换\n" +
//                "款式: 手提包\n" +
//                "流行款式名称: 水桶包","images/product_15.jpg");
//        goodRespository.save(good);
//        good = new Good("STAN SMITH PK 男网面透气休闲板鞋",59.90,"nomal",99,"产品名称: Adidas/阿迪达斯 CQ3032\n" +
//                "品牌: Adidas/阿迪达斯\n" +
//                "鞋码: 40 40.5 41 42 42.5 43 44\n" +
//                "颜色分类: CQ3032\n" +
//                "吊牌价: 1199.00\n" +
//                "款号: CQ3032\n" +
//                "上市时间: 2018年春季\n" +
//                "鞋帮高度: 低帮\n" +
//                "性别: 男子","images/product_16.jpg");
//        goodRespository.save(good);
//
//    }



}
