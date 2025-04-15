package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OnboardingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By welcomeScreenSkipButton = By.id("com.monefy.app.lite:id/buttonContinue");
    private static final By subscriptionCloseButton = By.id("com.monefy.app.lite:id/buttonClose");
    private static final By pieGraphContainer = By.id("com.monefy.app.lite:id/piegraph");

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final int MAX_WELCOME_SKIPS = 3;

    public OnboardingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        System.out.println("[INIT] OnboardingPage initialized with timeout: " + DEFAULT_TIMEOUT.getSeconds() + "s");
    }

    public void completeInitialAppLaunchFlow() {
        System.out.println("[FLOW] Starting app launch flow...");
        skipAllWelcomeScreensIfPresent();
        dismissSubscriptionPromptIfVisible();
        System.out.println("[FLOW] App launch flow completed.");
    }

    private boolean skipWelcomeScreenIfVisible() {
        try {
            WebElement skipButton = wait.until(ExpectedConditions.elementToBeClickable(welcomeScreenSkipButton));
            skipButton.click();
            System.out.println("[ACTION] Skipped one welcome screen");

            wait.until(ExpectedConditions.invisibilityOf(skipButton));
            return true;

        } catch (TimeoutException e) {
            System.out.println("[INFO] Welcome screen skip button not visible (possibly already skipped).");
            return false;

        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error while skipping welcome screen: " + e.getMessage());
            return false;
        }
    }

    private void skipAllWelcomeScreensIfPresent() {
        int skipped = 0;

        while (skipWelcomeScreenIfVisible()) {
            skipped++;
            if (skipped >= MAX_WELCOME_SKIPS) {
                System.out.println("[WARN] Reached max welcome screen skips (" + MAX_WELCOME_SKIPS + ")");
                break;
            }
        }

        System.out.println("[INFO] Finished skipping welcome screens. Total skipped: " + skipped);
    }

    private void dismissSubscriptionPromptIfVisible() {
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(subscriptionCloseButton));
            closeButton.click();
            System.out.println("[ACTION] Closed subscription prompt.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(pieGraphContainer));
            System.out.println("[INFO] Main screen is now visible.");

        } catch (TimeoutException e) {
            System.out.println("[INFO] Subscription prompt not shown or already dismissed.");

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to close subscription prompt: " + e.getMessage());
        }
    }
}
