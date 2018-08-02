package cn.jinks.webStudy.controller;

import cn.jinks.webStudy.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloControl {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("home")
    public String sayHello(){
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        System.out.println(remoteAddr);
        return "hello";
    }

    // 127.0.0.1:8080/hello/name?name=Jinks
    @RequestMapping(value="/name", method= RequestMethod.GET)
    public String modelTest(@RequestParam("name") String value, Model model){
        System.out.println(value);
        String name = value;
        model.addAttribute(name);

        //返回view的name
        return "hello";
    }
    // 127.0.0.1:8080/hello/name2/Jinks
    @RequestMapping(value="/name2/{name}", method=RequestMethod.GET)
    public String RestUrlTest(@PathVariable("name") String name, Map<String,Object> model){
        System.out.println(name);
        model.put("name",name);
        return "hello";
    }

    //127.0.0.1:8080/hello/name3?name=Jinks
    @RequestMapping("name3")
    public String TranditionalMeth(HttpServletRequest request){
        String name = request.getParameter("name");
        request.setAttribute("name",name);
        return "hello";
    }



    @RequestMapping(value="/admin", method=RequestMethod.POST, params="add")
    public String foo(){
        return "";
    }

    //binding
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String doSave(Student student){
        //biaodan yu Student ziduan name yiyi duiying

        return "redirect:name2/"+student.getName();
    }

    @RequestMapping(value="/save2", method=RequestMethod.POST)
    public String doSave2(@ModelAttribute Student student){
        //biaodan yu Student ziduan name yiyi duiying

        //redirect / forward
        return "redirect:name2/"+student.getName();
    }

    //127.0.0.1:8080/hello/file
    @RequestMapping(value="/file", method=RequestMethod.GET)
    public String showFileUpload(){
        return "file";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String doUpload(@RequestParam("file") MultipartFile mf, Map<String, Object> model){
        model.put("name", mf.getOriginalFilename());
        return "hello";
    }

    //JSON
    @RequestMapping(value="/{name}", method=RequestMethod.GET)
    public @ResponseBody Student getStudent(@PathVariable String name) {
        return new Student();
    }

    @RequestMapping(value="/json/{name}", method=RequestMethod.GET)
    public ResponseEntity<Student> getStudent2(@PathVariable String name) {
        return new ResponseEntity<Student>(new Student(), HttpStatus.OK);
    }
}
