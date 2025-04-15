package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;
import static java.util.Map.entry;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Number pad button locators
    private final Map<Character, By> keypadButtons = Map.ofEntries(
            entry('0', By.id("com.monefy.app.lite:id/buttonKeyboard0")),
            entry('1', By.id("com.monefy.app.lite:id/buttonKeyboard1")),
            entry('2', By.id("com.monefy.app.lite:id/buttonKeyboard2")),
            entry('3', By.id("com.monefy.app.lite:id/buttonKeyboard3")),
            entry('4', By.id("com.monefy.app.lite:id/buttonKeyboard4")),
            entry('5', By.id("com.monefy.app.lite:id/buttonKeyboard5")),
            entry('6', By.id("com.monefy.app.lite:id/buttonKeyboard6")),
            entry('7', By.id("com.monefy.app.lite:id/buttonKeyboard7")),
            entry('8', By.id("com.monefy.app.lite:id/buttonKeyboard8")),
            entry('9', By.id("com.monefy.app.lite:id/buttonKeyboard9")),
            entry('.', By.id("com.monefy.app.lite:id/buttonKeyboardDot"))
    );


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterAmount(String amount) {
        for (char digit : amount.toCharArray()) {
            By locator = keypadButtons.get(digit);
            if (locator != null) {
                clickElement(locator, "keypad '" + digit + "'");
            } else {
                System.err.println("[Warning] Unsupported character in amount: " + digit);
            }
        }
    }

    protected void clickElement(By locator, String description) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("[Action] Clicked on " + description);
        } catch (Exception e) {
            System.err.println("[Error] Failed to click on " + description + ": " + e.getMessage());
        }
    }

    protected void enterText(By locator, String text, String description) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            field.clear();
            field.sendKeys(text);
            System.out.println("[Action] Entered text '" + text + "' in " + description);
        } catch (Exception e) {
            System.err.println("[Error] Failed to enter text in " + description + ": " + e.getMessage());
        }
    }
}
