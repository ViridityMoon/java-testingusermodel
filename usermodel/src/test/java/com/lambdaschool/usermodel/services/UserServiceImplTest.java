package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

// Service Implementation Using the Database
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
public class UserServiceImplTest
{

    @Autowired
    private UserService userService;

    // for w/o a database
    // Make data in setUp
//    @MockBean
//    private UserRepository userrepos;

    @Before
    public void setUp() throws Exception
    {
        // mocks => fake data
        // stubs => fake methods
        MockitoAnnotations.initMocks(this);

        List<User> myList = userService.findAll();
        for (User u : myList)
        {
            System.out.println(u.getUserid());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findUserById()
    {
        assertEquals("testadmin", userService.findUserById(4).getUsername());
    }

    @Test
    public void findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("admin").size());
        assertEquals(5, userService.findByNameContaining("test").size());

    }

    @Test
    public void findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void delete()
    {
//        assertNotNull(userService.findUserById(4));
        userService.delete(4);
//        assertEquals(4, userService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteIdNotFound()
    {
        userService.delete(1);
    }

    @Test
    public void findByName()
    {
        assertEquals(4, userService.findByName("testadmin").getUserid());
    }

    @Test (expected = EntityNotFoundException.class)
    public void findByNameNotFound()
    {
        assertEquals(4, userService.findByName("test").getUserid());
    }

    @Test
    public void saveadd()
    {
        String user2Var = "testadding";
        User u = new User(user2Var, "password", "test@test.com");

        Role r1 = new Role("testRole1");
        r1.setRoleid(1);
        Role r2 = new Role("testRole2");
        r2.setRoleid(2);
        u.getRoles().add(new UserRoles(u, r1));
        u.getRoles().add(new UserRoles(u, r2));

        u.getUseremails().add(new Useremail(u, "test@email.local"));
        u.getUseremails().add(new Useremail(u, "test@mymail.local"));

        User addUser = userService.save(u);
        assertNotNull(addUser);
        assertEquals(user2Var, addUser.getUsername());

    }

    @Test
    public void saveput()
    {
        String user2Var = "testadding";
        User u = new User(user2Var, "password", "test@test.com");
        u.setUserid(10);
        Role r1 = new Role("testRole1");
        r1.setRoleid(1);
        Role r2 = new Role("testRole2");
        r2.setRoleid(2);
        u.getRoles().add(new UserRoles(u, r1));
        u.getRoles().add(new UserRoles(u, r2));

        u.getUseremails().add(new Useremail(u, "test@email.local"));
        u.getUseremails().add(new Useremail(u, "test@mymail.local"));
    }

    @Test
    public void update()
    {

    }

    @Test
    public void deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}