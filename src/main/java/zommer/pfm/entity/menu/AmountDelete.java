package zommer.pfm.entity.menu;

import zommer.pfm.model.Amount;
import zommer.pfm.model.Category;

import java.util.Optional;

public final class AmountDelete extends AbstractMenu {
    public AmountDelete() {
        super("Удаление платежа");
    }

    @Override
    public void accept(Context context) {
        // Выбираем категорию
        Optional<Category> optionalCategory = context.service.selectCategoryMenu(context, false);
        if (optionalCategory.isEmpty()) {
            return;
        }

        Optional<Amount> optionalAmount = context.service.selectAmountMenu(context, optionalCategory.get(), false);
        if (optionalAmount.isEmpty()) {
            return;
        }
        Amount amount = optionalAmount.get();

        String select = context.selectString("Вы уверены что хотите удалить?[y/N]");
        if (!select.equalsIgnoreCase("y")) {
            return;
        }

        context.service.removeAmount(amount).ifPresent(message -> {
            context.errorln("Ошибка удаления. %s", message);
        });

        context.service.storeRepository();

        context.println("ПЛАТЁЖ УДАЛЁН");
        context.println("");
    }
}
