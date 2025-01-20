package zommer.pfm.entity.menu;

public final class UserList extends AbstractMenu {
    public UserList() {
        super("Список пользователей");
    }

    @Override
    public void accept(Context context) {
        context.service.selectUserMenu(context, true);

        context.println("");
    }
}
