package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends BasePage {

    private static final By overflowMenuButton = By.id("com.monefy.app.lite:id/overflow");
    private static final By accountsMenuButton = By.id("com.monefy.app.lite:id/accounts_imagebutton");

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the overflow menu and navigates to the Accounts section.
     */
    public void openAccountsSection() {
        clickElement(overflowMenuButton, "Overflow menu");
        clickElement(accountsMenuButton, "Accounts section");
    }
}
