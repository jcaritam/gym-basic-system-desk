package com.jc.gymbasicsystemfront;
import com.jc.gymbasicsystemfront.views.auth.LoginView;
import javax.swing.*;

public class Gymbasicsystemfront {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
