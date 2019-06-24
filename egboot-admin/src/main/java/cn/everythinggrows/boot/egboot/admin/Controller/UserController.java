package cn.everythinggrows.boot.egboot.admin.Controller;


import cn.everythinggrows.boot.egboot.admin.dao.AdminUserDao;
import cn.everythinggrows.boot.egboot.admin.model.egUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private AdminUserDao adminUserDao;

    @RequestMapping("/admin/index/user")
    public String adminUser(HttpServletRequest request){
        List<egUser> userList = new ArrayList<>();
        for(int i=0;i<=7;i++){
            for(int j=0;j<=31;j++){
                String tableName = "eg_user_" + String.valueOf(j);
                List<egUser> user = adminUserDao.selectEgUser(i,tableName);
                userList.addAll(user);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("userList",userList);
        return "lw-admin-user";
    }

    @RequestMapping("/admin/user/delete/{uid}")
    public String deleteUser(@PathVariable("uid") long uid){
        adminUserDao.deleteUser(uid);
        return "userDelete";
    }

    @RequestMapping("/logup")
    public String getlog(){
        return "logup";
    }
}
