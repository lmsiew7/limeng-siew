package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By incomeButton = By.id("com.monefy.app.lite:id/income_button");
    private static final By balanceAmountLabel = By.id("com.monefy.app.lite:id/balance_amount");
    private static final By houseCategoryIcon = By.xpath(
            "//android.widget.FrameLayout[@resource-id='com.monefy.app.lite:id/piegraph']/android.widget.ImageView[5]"
    );

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddIncome() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(incomeButton));
            button.click();
            System.out.println("[Action] Clicked on the Income button.");
        } catch (Exception e) {
            System.err.println("[Error] Failed to click on Income button: " + e.getMessage());
        }
    }

    public void clickHouseCategory() {
        try {
            WebElement category = wait.until(ExpectedConditions.elementToBeClickable(houseCategoryIcon));
            category.click();
            System.out.println("[Action] Clicked on House category.");
        } catch (Exception e) {
            System.err.println("[Error] Failed to click on House category: " + e.getMessage());
        }
    }

    public Optional<BigDecimal> getBalanceAmount() {
        try {
            WebElement balanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(balanceAmountLabel));
            String rawBalance = balanceElement.getText();

            // Clean the balance string: keep digits, dot, and minus
            String numericText = rawBalance.replaceAll("[^\\d.-]", "");

            BigDecimal balance = new BigDecimal(numericText);
            System.out.println("[Info] Retrieved balance: " + balance);
            return Optional.of(balance);

        } catch (Exception e) {
            System.err.println("[Error] Failed to retrieve balance amount: " + e.getMessage());
            return Optional.empty();
        }
    }
}