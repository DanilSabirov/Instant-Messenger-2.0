package net.myCompany.servlets;

import net.myCompany.database.ConnectionManager;
import net.myCompany.database.repositories.UserRepository;
import net.myCompany.database.repositories.UserRepositoryImpl;
import net.myCompany.database.repositories.specifications.GetUserById;
import net.myCompany.database.user.UserIM;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    public MainServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        UserRepository userRepository = new UserRepositoryImpl(connection);

        UserIM user = getUser(userRepository, session);
        if (user != null) {

        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserIM getUser(UserRepository repository, HttpSession session) {
        List<UserIM> users = null;
        try {
            users = repository.query(new GetUserById(Integer.getInteger((String)session.getAttribute("id"))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users.size() == 1) {
            UserIM user = users.get(0);

        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
