package net.myCompany.servlets;

import net.myCompany.ConfigurationManager;
import net.myCompany.database.AuthenticationData;
import net.myCompany.database.ConnectionManager;
import net.myCompany.database.repositories.UserRepository;
import net.myCompany.database.repositories.UserRepositoryImpl;
import net.myCompany.database.repositories.specifications.GetUserByEmailPassword;
import net.myCompany.database.user.User;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    public LoginServlet() {super();}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserRepository userRepository = new UserRepositoryImpl(connection);
        String email = req.getParameter("email");
        char[] password = req.getParameter("password").toCharArray();
        List<UserIM> users = null;

        try {
            users = userRepository.query(new GetUserByEmailPassword(new AuthenticationData(email, password)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (users.size() == 1) {
            UserIM user = users.get(0);
            HttpSession session = req.getSession();
            session.setAttribute("id", user.getId());
            session.setAttribute("name", user.getName());
            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.mainUserPage")).forward(req, resp);
        }
        else {
            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(req, resp);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {

    }
}
