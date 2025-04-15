package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountsPage extends BasePage {

    private final By addAccountButton = By.id("com.monefy.app.lite:id/imageButtonAddCategory");
    private final By accountNameField = By.id("com.monefy.app.lite:id/editTextCategoryName");
    private final By initialAmountField = By.id("com.monefy.app.lite:id/initialAmount");
    private final By accountTypeIcon = By.xpath("(//android.widget.ImageView[@resource-id='com.monefy.app.lite:id/imageView'])[1]");
    private final By saveAccountButton = By.id("com.monefy.app.lite:id/save");
    private final By accountListItems = By.id("com.monefy.app.lite:id/relativeLayoutManageCategoriesListItem");
    private final By accountName = By.id("com.monefy.app.lite:id/textViewName");


    public AccountsPage(WebDriver driver) {
        super(driver);
    }

    public void tapAddAccount() {
        clickElement(addAccountButton, "Add Account");
    }

    public void enterAccountName(String name, String fieldLabel) {
        enterText(accountNameField, name, fieldLabel);
    }

    public void setInitialAmount(String amount, String fieldLabel) {
        enterText(initialAmountField, amount, fieldLabel);
    }

    public void selectAccountTypeIcon() {
        clickElement(accountTypeIcon, "Account Type Icon");
    }

    public void saveAccount() {
        clickElement(saveAccountButton, "Save Account");
    }

    public boolean isAccountPresent(String expectedName) {
        List<WebElement> accountItems = driver.findElements(accountListItems);
        System.out.println("[Check] Searching for account: " + expectedName);

        for (WebElement item : accountItems) {
            // Find the child TextView inside the item
            String actualName = item.findElement(accountName).getText().trim();
            System.out.println("→ Found account: " + actualName);

            if (actualName.equalsIgnoreCase(expectedName)) {
                return true;
            }
        }
        return false;
    }

}
