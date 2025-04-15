package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExpensePage extends BasePage {

    private static final By confirmExpenseButton = By.id("com.monefy.app.lite:id/keyboard_action_button");

    public ExpensePage(WebDriver driver) {
        super(driver);
    }

    public void confirmExpense() {
        System.out.println("[Action] Tapping 'Confirm Expense' button");
        clickElement(confirmExpenseButton, "Confirm Expense");
    }
}
