package cn.everythinggrows.boot.egboot.admin.Controller;

import cn.everythinggrows.boot.egboot.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminUserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping(value = "/admin/user/logup")
    public String logUp(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password){

        String sql="select * from eg_admin_user";
        List<User> list=jdbcTemplate.query(sql,new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
        if(list.size() == 0){
            return "logup";
        }
        for(User user : list){
            if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
                return "admin-index";
            }
        }
        return "logup";
    }

}
