package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IncomePage extends BasePage {

    private static final By confirmIncomeButton = By.id("com.monefy.app.lite:id/keyboard_action_button");
    private static final By salaryCategoryButton = By.xpath(
            "//android.widget.GridView[@resource-id='com.monefy.app.lite:id/gridViewCategories']/android.widget.FrameLayout[2]"
    );

    public IncomePage(WebDriver driver) {
        super(driver);
    }

    public void confirmIncome() {
        System.out.println("[Action] Confirming income entry");
        clickElement(confirmIncomeButton, "Confirm Income");
    }

    public void selectSalaryCategory() {
        System.out.println("[Action] Selecting 'Salary' category");
        clickElement(salaryCategoryButton, "Salary Category");
    }
}
