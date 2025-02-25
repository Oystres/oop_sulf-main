package zommer.pfm;

import zommer.pfm.adapter.Storage;
import zommer.pfm.entity.menu.*;
import zommer.pfm.entity.security.Authentication;
import zommer.pfm.repository.Repository;
import zommer.pfm.service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        Storage storage = new Storage.FileStorage("storage.json");
        Repository repo = new Repository.Memory(storage);
        Authentication authentication = Authentication.create(repo);
        Service service = new Service.Default(repo, authentication);
        repo.load();

        // При завершении приложения будем сохранять данные
        Runtime.getRuntime().addShutdownHook(new Thread(repo::store));

        Menu menu = createMenu(repo);

        try (Context context = new Context(service)) {
            menu.select(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        repo.store();
    }

    private static Menu createMenu(Repository repo) {
        Menu root = Menu.root();

        Menu userMenu = root.submenu("Управление пользователями");
        Authentication authentication = Authentication.create(repo);
        new AuthenticationByLogin(authentication).register(userMenu);
        new UserList().register(userMenu);
        new Registration(repo).register(userMenu);

        Menu walletMenu = root.submenu("Управление кошельками");
        new WalletSelect().register(walletMenu);
        new WalletList().register(walletMenu);
        new WalletCreate().register(walletMenu);
        new WalletEdit().register(walletMenu);
        new WalletDelete().register(walletMenu);

        Menu categoryMenu = root.submenu("Управление категориями");
        new CategoryCreate().register(categoryMenu);
        new CategoryList().register(categoryMenu);
        new CategoryEdit().register(categoryMenu);
        new CategoryDelete().register(categoryMenu);

        Menu amountMenu = root.submenu("Управление платежами");
        new AmountCreate().register(amountMenu);
        new AmountList().register(amountMenu);
        new AmountEdit().register(amountMenu);
        new AmountDelete().register(amountMenu);

        Menu transferMenu = root.submenu("Перевод средств пользователям");
        new TransferMenu().register(transferMenu);

        Menu statisticMenu = root.submenu("Статистика");
        new StatisticUser().register(statisticMenu);
        new StatisticWallet().register(statisticMenu);

        return root;
    }
}
