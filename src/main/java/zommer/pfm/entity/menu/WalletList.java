package zommer.pfm.entity.menu;

import zommer.pfm.entity.security.Authentication;
import zommer.pfm.model.User;

import java.util.Optional;

public final class WalletList extends AbstractMenu {
    public WalletList() {
        super("Список кошельков");
    }

    @Override
    public void accept(Context context) {
        // Получаем аутентифицированного пользователя
        Optional<User> sessionUser = context.authorized().map(Authentication.Session::user);

        // Если пользователь не аутентифицирован, то сообщаем, что данный пункт меню для него недоступен
        if (sessionUser.isEmpty()) {
            context.errorln("Требуется аутентификация");
            return;
        }

        context.service.selectWalletMenu(context, true);
        context.println("");
    }
}
