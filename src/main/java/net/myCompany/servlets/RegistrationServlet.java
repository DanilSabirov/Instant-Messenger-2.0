package net.myCompany.servlets;

import net.myCompany.ConfigurationManager;
import net.myCompany.database.AuthenticationData;
import net.myCompany.database.ConnectionManager;
import net.myCompany.database.repositories.AuthenticationDataRepository;
import net.myCompany.database.repositories.AuthenticationDataRepositoryImpl;
import net.myCompany.database.repositories.UserRepository;
import net.myCompany.database.repositories.UserRepositoryImpl;
import net.myCompany.database.repositories.specifications.GetUserByEmail;
import net.myCompany.database.user.User;
import net.myCompany.database.user.UserIM;
import net.myCompany.database.user.UserImpl;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet{
    public RegistrationServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuthenticationDataRepository authenticationDataRepository = new AuthenticationDataRepositoryImpl(connection);
        UserRepository userRepository = new UserRepositoryImpl(connection);

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        char[] password = req.getParameter("password").toCharArray();

        try {
            userRepository.add(new UserImpl(name, email));
            UserIM userIM = userRepository.query(new GetUserByEmail(email)).get(0);
            authenticationDataRepository.add(new AuthenticationData(email, password), userIM);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.close();
                req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.registration")).forward(req, resp);
                return;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            connection.commit();
            connection.close();
            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.getRequestDispatcher(ConfigurationManager.getProperty("path.page.registration")).forward(req, resp);
        }
    }


}
