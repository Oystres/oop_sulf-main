package zommer.pfm.entity.menu;

import zommer.pfm.model.Category;

import java.util.Optional;

public final class AmountList extends AbstractMenu {
    public AmountList() {
        super("Список платежей");
    }

    @Override
    public void accept(Context context) {
        // Выбираем категорию
        Optional<Category> optionalCategory = context.service.selectCategoryMenu(context, false);
        if (optionalCategory.isEmpty()) {
            return;
        }

        context.service.selectAmountMenu(context, optionalCategory.get(), true);
        context.println("");
    }
}
