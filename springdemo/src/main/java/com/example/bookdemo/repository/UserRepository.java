
package com.example.bookdemo.repository;

        import com.example.bookdemo.entity.User;
        import com.example.bookdemo.entity.UserAuth;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findAllByUsername(String username);

    User findByUsername(String username);
    User findByEmail(String email);



    // 在需要自定义查询方法时，可以在这里添加额外的方法声明
}
