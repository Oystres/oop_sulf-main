package zommer.pfm.entity.menu;

import zommer.pfm.entity.security.Authentication;

import java.util.Optional;

public final class AuthenticationByLogin extends AbstractMenu {
    private final Authentication authentication;

    public AuthenticationByLogin(Authentication authentication) {
        super("Аутентификация по логину/паролю");
        this.authentication = authentication;
    }

    @Override
    public void accept(Context context) {
        String login = context.selectString("Введите логин");
        String password = context.selectString("Введите пароль");
        Optional<Authentication.Session> session = this.authentication.authenticate(login, password);
        if (session.isEmpty()) {
            context.clearSession();
            context.errorln("Не удалось аутентифицироваться под пользователем '%s'", login);
            return;
        }
        session.ifPresent(sess -> {
            context.putSession(sess);
            String lastWalletId = sess.user().getLastWalletId();
            if (lastWalletId != null) {
                context.service.getWallet(lastWalletId).ifPresent(sess::setWallet);
            }
        });

        session.ifPresent(context::putSession);

        context.println("ПОЛЬЗОВАТЕЛЬ АУТЕНТИФИЦИРОВАН");
        context.println("");
    }
}
