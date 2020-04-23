package com.lagou.edu.controller;

import com.lagou.edu.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/handle01")
    public ModelAndView handle01(){

        int i = 1/0;
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }




    /**
     * SpringMVC在handler方法上传入Map、Model、ModelMap参数，
     * 并向这些参数中保存数据（放入到了请求域中），都也在页面获取到
     * 他们之间的关系：
     *    通过上面代码的打印信息可知：
     * modelMap：class org.springframework.validation.support.BindingAwareModelMap
     * model：class org.springframework.validation.support.BindingAwareModelMap
     * map：class org.springframework.validation.support.BindingAwareModelMap
     * 运行时的具体类型都是BindingAwareModelMap，相当于给BindingAwareModelMap中保存的数据都会放在请求域中
     *
     * Map（jdk中的接口）
     * Model(spring框架中的接口)
     * ModelMap(spring框架中的类，集成了LinkedHashMap，LinkedHashMap又是Map接口的实现类，所以它本质就是Map接口的实现类)
     * BindingAwareModelMap（继承了ExtendedModelMap类，而ExtendedModelMap类继承了ModelMap且实现了Model接口）
     */




    /**
     * 直接声明形参ModelMap封装数据
     */
    @RequestMapping("/handle02")
    public String handle02(ModelMap modelMap){
        Date date = new Date(); //服务器时间
        modelMap.addAttribute("Date",date);
        System.out.println("modelMap：" + modelMap.getClass());
        return "success";
    }

    /**
     * 直接声明形参Model封装数据
     */
    @RequestMapping("/handle03")
    public String handle03(Model model){
        Date date = new Date(); //服务器时间
        model.addAttribute("Date",date);
        System.out.println("model：" + model.getClass());
        return "success";
    }


    /**
     * 直接声明形参Map封装数据
     */
    @RequestMapping("/handle04")
    public String handle04(Map<String,Object> map){
        Date date = new Date(); //服务器时间
        map.put("Date",date);
        System.out.println("map：" + map.getClass());
        return "success";
    }


    /**
     * 日期类型参数绑定
     * 访问url: localhost:8080/handle05?birthday=2020-04-09
     * 接收birthday是个String类型，在handler方法中根本接收不到，会报类型转换异常。
     * 此时需要自定义一个类型转换器（实现Converter接口，注册到SpringMVC即可）
     * 自定义类型转换器类：
     *
     */
    @RequestMapping("/handle05")
    public ModelAndView handle05(Date birthday){
        Date date = new Date();
        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * springmvc对RESTful风格的支持
     * 使用@PathVariable注解
     * 请求url: get请求(查询) /demo/handle/15
     */
    @RequestMapping(value="/handle/{id}",method = {RequestMethod.GET})
    public ModelAndView handleGet(@PathVariable("id") Integer id){
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * springmvc对RESTful风格的支持
     * 使用@PathVariable注解
     * 请求url: post请求(新增) /demo/handle 请求参数以表单方式提交：张三
     * 注意：此时获取到的参数实际上是个乱码，所以在web.xml需要配置一个过滤器
     * get请求中如果出现乱码，解决方式为：需要修改tomcat下server.xml中的<Connector/>标签中的URIEncoding属性
     */
    @RequestMapping(value="/handle",method = {RequestMethod.POST})
    public ModelAndView handlePost(String username){
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * springmvc对RESTful风格的支持
     * <form method="post action="/demo/handle/15/zhangsan">
     *     <input type="hidden" name="_method" value="put"/>
     *     <input type="submit" value="提交rest_put请求"/>
     * </form>
     * 提交put请求时，需要添加一个隐藏域，name属性固定传入_method，value传入put
     * 我们还需要添加一个过滤器在web.xml，拦截该请求，然后判断该请求中有没有一个参数为_method，有就会转换成put
     * 使用@PathVariable注解
     * 请求url: put请求(更新) /demo/handle/15/zhangsan
     */
    @RequestMapping(value="/handle/{id}/{name}",method = {RequestMethod.PUT})
    public ModelAndView handlePut(@PathVariable("id") Integer id,@PathVariable("name") String username){
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * springmvc对RESTful风格的支持
     * <form method="post action="/demo/handle/15">
     *     <input type="hidden" name="_method" value="delete"/>
     *     <input type="submit" value="提交rest_put请求"/>
     * </form>
     * 提交delete请求时，需要添加一个隐藏域，name属性固定传入_method，value传入delete
     * 我们还需要添加一个过滤器在web.xml，拦截该请求，然后判断该请求中有没有一个参数为_method，有就会转换成put
     * 使用@PathVariable注解
     * 请求url: delete请求(删除) /demo/handle/15/
     */
    @RequestMapping(value="/handle/{id}",method = {RequestMethod.DELETE})
    public ModelAndView handleDelete(@PathVariable("id") Integer id){
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * 前台到后台的交互：json转成pojo
     * 使用注解@RequestBody
     */
//    @RequestMapping("/handleAjax")
//    public ModelAndView handleAjax(@RequestBody User user){
//        Date date = new Date(); //服务器时间
//
//        //返回服务器时间到前端页面
//        //ModelAndView：封装了数据和页面信息的模型
//        ModelAndView modelAndView = new ModelAndView();
//        //addObject 其实是向请求域中放入了一个值
//        //相当于request.setAttribute("Date",date);
//        modelAndView.addObject("Date",date);
//
//        //视图信息（逻辑视图名）
//        modelAndView.setViewName("success");
//        return modelAndView;
//    }
//
//    /**
//     * 后台带前台的交互：pojo转成json
//     * 使用注解@ResponseBody，此时不再走视图解析器，而是等同于response响应直接输出数据
//     *
//     */
//    @RequestMapping("/handleAjax")
//    @ResponseBody
//    public User handleAjax2(@RequestBody User user){
//        //业务逻辑处理，修改name为张三
//        user.setName("张三");
//        return user;
//    }

    /**
     * 文件上传
     * @param uploadFile 就是上传过来的文件
     * @return
     */
    @RequestMapping("/upload")
    public ModelAndView upload(MultipartFile uploadFile, HttpSession session) throws IOException {
        //处理文件上传
        //1.重命名，获取后缀(扩展名)
        String originalFilename = uploadFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        //生成新名称
        String newName = UUID.randomUUID().toString() + "." + ext;
        //存储 要存储到指定的文件夹 如：/uploads/yyyy-MM-dd
        //要考虑同一个文件夹内文件过多，我们会按照日期创建子目录
        //获取真实磁盘路径
        String realPath = session.getServletContext().getRealPath("/uploads");
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File folder = new File(realPath+"/"+datePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        //存储文件到目录
        uploadFile.transferTo(new File(folder,newName));
        // TODO 文件磁盘路径要更新到数据库字段

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }


//    //springmvc的异常处理机制（异常处理器）
//    //注意：写在这里只会对当前controller类生效
//    @ExceptionHandler(ArithmeticException.class)
//    public void handleException(ArithmeticException exception, HttpServletResponse response){
//        //在handle01中添加了个int i = 1/0;
//        //异常处理逻辑
//        try {
//            response.getWriter().write(exception.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * springmvc 重定向时，参数传递的问题
     * 转发：A找B借400块钱，B没有钱但是悄悄地找到C借了400块钱给A
     *      （url不会变，参数也不会丢失，是同一个请求）
     * 重定向：A找B借钱, B说我没有钱，你找别人去，那么A又带着借400块钱的需求找到C
     *      （url会变，参数会丢失需要重新携带参数，是两个个请求）
     */

    @RequestMapping("/handleRedirect")
    public String handleRedirect(String name, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("name",name);
//        return "redirect:handle05";  //该方式传递到handler05时获取不到name
//        return "redirect:handle05?name="+name; //该方式可以解决上述问题，但是属于get方式传参，拼接参数安全性、参数长度都有局限。
        //可以使用flash属性
        //addFlashAttribute方法设置了一个flash类型的属性，该属性会被暂存到session中，在跳转到页面之后该属性销毁。
        redirectAttributes.addFlashAttribute("name",name);
        return "redirect:handle05";
    }


    @RequestMapping("/handle05")
    public ModelAndView handle05(@ModelAttribute("name") String name){

//        int i = 1/0;
        Date date = new Date(); //服务器时间

        //返回服务器时间到前端页面
        //ModelAndView：封装了数据和页面信息的模型
        ModelAndView modelAndView = new ModelAndView();
        //addObject 其实是向请求域中放入了一个值
        //相当于request.setAttribute("Date",date);
        modelAndView.addObject("Date",date);

        //视图信息（逻辑视图名）
        modelAndView.setViewName("success");
        return modelAndView;
    }

}
