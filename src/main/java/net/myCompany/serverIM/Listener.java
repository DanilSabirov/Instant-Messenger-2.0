package net.myCompany.serverIM;

import net.myCompany.serverIM.database.AuthenticationData;
import net.myCompany.serverIM.database.Database;
import net.myCompany.serverIM.database.dialog.GroupDialog;
import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.user.User;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener implements Runnable, Observable {
    private static Logger log = Logger.getLogger(Listener.class.getName());

    private Socket client;

    private Database database;

    private ExecutorService speaker = Executors.newSingleThreadExecutor();

    private User curUser = null;

    public Listener(Socket client, Database database) {
        this.client = client;
        this.database = database;
    }

    @Override
    public void run() {
        log.info("New connection: " + Thread.currentThread().getId());

    }

    private void listen() throws IOException {

    }

    private AuthenticationData listenAuthenticationData() throws XMLStreamException {

        return null;
    }

    private User listenUserData()  {

        return null;
    }

    private Message listenMessage() {

        return null;
    }

    private void sendAuthenticationRequest(int deep) {

    }

    private void sendAuthenticationResponse(User response) {

    }

    private void sendRegistrationResponse(String response) {

    }

    private void sendFoundUsers(List<User> users) {

    }

    private void sendUser(User user, int deep) {

    }

    private void sendDialog(GroupDialog dialog) {

    }

    private void sendMessage(Message message, int deep) throws XMLStreamException {
        log.info("Sending message: " + message);


        log.info("Message sent");
    }


    @Override
    public void notifyOfMessage(Message message) {
        speaker.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(message, 1);
                } catch (XMLStreamException e) {
                    log.log(Level.SEVERE, "Exception: ", e);
                }
            }
        });
    }

    @Override
    public void notifyOfDialog(GroupDialog dialog) {

    }

    @Override
    public User getUser() {
        return curUser;
    }
}
