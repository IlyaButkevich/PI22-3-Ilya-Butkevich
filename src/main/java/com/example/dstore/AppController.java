package com.example.dstore;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;
import java.util.Comparator;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
public class AppController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MyAppUserService myAppUserService;

    @Autowired
    private BasketService basketService;



    int currentshop;
    String cursearch;

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        return "index";
    }

    @RequestMapping("/toshop1")
    public String shop1(Model model) {
        currentshop=1;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        List<Goods> goodsList = goodsService.ListAll("бытовая техника");
        model.addAttribute("goodsList", goodsList);
        return "shop";
    }

    @RequestMapping("/toshop2")
    public String shop2(Model model) {
        currentshop=2;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        List<Goods> goodsList = goodsService.ListAll("все к пк");
        model.addAttribute("goodsList", goodsList);
        return "shop";
    }

    @RequestMapping("/toshop3")
    public String shop3(Model model) {
        currentshop=3;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        List<Goods> goodsList = goodsService.ListAll("прочее");
        model.addAttribute("goodsList", goodsList);
        return "shop";
    }


    @RequestMapping("/toshop4")
    public String shop4(Model model, @Param("keyword") String keyword) {
        currentshop=4;
        if (keyword!=null){cursearch=keyword;}
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        List<Goods> goodsList = goodsService.FindName(cursearch);
        model.addAttribute("goodsList", goodsList);
        return "shop";
    }

    boolean sorted = true;
    @RequestMapping("sort")
    public  String sortHomePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "Вы вошли как " + auth.getName();
        model.addAttribute("username", username);

        List<Goods> goodsList = null;
        if (currentshop==1){goodsList = goodsService.ListAll("бытовая техника");}
        if (currentshop==2){goodsList = goodsService.ListAll("все к пк");}
        if (currentshop==3){goodsList = goodsService.ListAll("прочее");}
        if (currentshop==4){goodsList = goodsService.ListAll(cursearch);}


        goodsList.sort(Comparator.comparing(Goods::getPrice));

        if (sorted==false){
            goodsList.sort(Comparator.comparing(Goods::getPrice).reversed());
        }

        model.addAttribute("goodsList", goodsList);
        sorted = !sorted;
        return "shop";
    }


    @RequestMapping("/addgood")
    public  String showNewStudentForm(Model model){
        Goods goods = new Goods();
        model.addAttribute("goods", goods);
        return "add";
    }

    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public String saveFirmware(@ModelAttribute("goods") Goods goods, MultipartFile file) throws IOException {
        goods.setImage(file.getBytes());
        goodsService.save(goods);

        if (currentshop==1){return "redirect:/toshop1";}
        if (currentshop==2){return "redirect:/toshop2";}
        if (currentshop==3){return "redirect:/toshop3";}
        if (currentshop==4){return "redirect:/toshop4";}

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteGoods(@PathVariable Long id){
        goodsService.delete(id);

        if (currentshop==1){return "redirect:/toshop1";}
        if (currentshop==2){return "redirect:/toshop2";}
        if (currentshop==3){return "redirect:/toshop3";}
        if (currentshop==4){return "redirect:/toshop4";}

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditStudentForm(@PathVariable(name = "id") Long id){
        ModelAndView mav = new ModelAndView("edit");
        Goods goods = goodsService.get(id);
        mav.addObject("goods", goods);
        return mav;
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public String saveFirmwar(@ModelAttribute("goods") Goods goods, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Goods existing = goodsService.get(goods.getId());
            existing.setDescription(goods.getDescription());
            existing.setGoodname(goods.getGoodname());
            existing.setGoodtype(goods.getGoodtype());
            existing.setQuantity(goods.getQuantity());
            existing.setPrice(goods.getPrice());

        }
        else {
            goods.setImage(file.getBytes());
            goodsService.save(goods);
        }

        if (currentshop==1){return "redirect:/toshop1";}
        if (currentshop==2){return "redirect:/toshop2";}
        if (currentshop==3){return "redirect:/toshop3";}
        if (currentshop==4){return "redirect:/toshop4";}

        return "redirect:/";
    }


    @RequestMapping("/addtocart/{id}")
    public  String tocart(@PathVariable(name = "id") Long id){
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        Goods goods = goodsService.get(id);
        String gn = goods.getGoodname();
        int pr = goods.getPrice();
        int leftitem = goods.getQuantity();

        goods.setQuantity(leftitem-1);
        goodsService.save(goods);

        Basket basket = new Basket();
        basket.setGoodname(gn);
        basket.setPrice(pr);
        basket.setUsername(auth);
        basketService.save(basket);
        if (currentshop==1){return "redirect:/toshop1";}
        if (currentshop==2){return "redirect:/toshop2";}
        if (currentshop==3){return "redirect:/toshop3";}
        if (currentshop==4){return "redirect:/toshop4";}

        return "redirect:/";
    }

    @RequestMapping("/cartpage")
    public  String tocartpage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String un = auth.getName();
        model.addAttribute("un", un);
        model.addAttribute("cartList", basketService.ListAll());
        return "shopcart";
    }

    @PostMapping("/deletecart/{id}")
    public String deleteCarts(@PathVariable Long id){
        basketService.delete(id);
        return "redirect:/cartpage";
    }

    @RequestMapping("/toconfirm")
    public  String toconfirm(){
        basketService.deletebyuser(SecurityContextHolder.getContext().getAuthentication().getName());
        return "confirm";
    }

    @RequestMapping("/toabout")
    public  String toabout(){
        return "about";
    }


    @RequestMapping("toadminpanel")
    public String toadminpanel(Model model){
        List<Myappuser> myappuserList = myAppUserService.findAll();
        model.addAttribute("myappuserList", myappuserList);



        Map<String, Integer> dateMap = new HashMap<>();

        for (Myappuser myappuser : myappuserList) {
            String dateGood = myappuser.getRole();
            dateMap.put(dateGood, dateMap.getOrDefault(dateGood, 0) + 1);
        }

        List<List<Object>> dateCountMap = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : dateMap.entrySet()) {
            List<Object> subList = new ArrayList<>();
            subList.add(entry.getKey());
            subList.add(entry.getValue());
            dateCountMap.add(subList);
        }

        model.addAttribute("chartData", dateCountMap);


        return "adminpanel";
    }

    @PostMapping("/updateRole/{id}")
    public String updateRole(@PathVariable Long id, @RequestParam("role") String role) {
        Myappuser user = myAppUserService.get(id);
        if (user != null) {
            user.setRole(role);
            myAppUserService.save(user); // Update the user in the database
        }
        return "redirect:/toadminpanel"; // Redirect back to the admin panel
    }




    @Autowired
    private MyAppUserRepository myAppUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/req/signup", consumes = "application/x-www-form-urlencoded")
    public String createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        Myappuser user = new Myappuser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        myAppUserRepository.save(user);
        return "login";
    }


    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping("tologout")
    public String toLogout(){
        return "redirect:/";
    }

}
