import com.asule.UserCenterApplication;
import com.asule.dao.UserMapper;
import com.asule.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class UserCenterTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMapper(){

        User user = new User();
        user.setWxNickname("asule");
        user.setId(1);
        user.setWxId("909090");

        userMapper.insert(user);


    }


}
