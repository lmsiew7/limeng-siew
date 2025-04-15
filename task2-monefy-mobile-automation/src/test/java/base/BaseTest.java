package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    private static final String DEFAULT_APPIUM_SERVER = "http://127.0.0.1:4723";
    private static final String DEFAULT_APK_PATH = System.getProperty("user.dir") + "/apk/monefy.apk";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);
    private static final String PLATFORM_ANDROID = "Android";
    private static final String DEVICE_NAME = "Pixel_3a_API_36";
    private static final String AUTOMATION_NAME_UIAUTOMATOR2 = "UiAutomator2";
    private static final String APP_PACKAGE = "com.monefy.app.lite";
    private static final String APP_WAIT_ACTIVITY = "com.monefy.activities.onboarding.OnboardingActivity_*";

    private static URL getAppiumServerUrl() throws MalformedURLException {
        String envUrl = System.getenv("APPIUM_SERVER");
        return new URL(envUrl != null && !envUrl.isEmpty() ? envUrl : DEFAULT_APPIUM_SERVER);
    }

    private static String getApkPath() {
        return System.getenv().getOrDefault("APK_PATH", DEFAULT_APK_PATH);
    }

    @BeforeClass
    public void initializeDriver() {
        System.out.println("[INIT] Starting AndroidDriver setup...");

        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName(PLATFORM_ANDROID)
                    .setDeviceName(DEVICE_NAME)
                    .setAutomationName(AUTOMATION_NAME_UIAUTOMATOR2)
                    .setApp(getApkPath())
                    .setNewCommandTimeout(DEFAULT_TIMEOUT)
                    .setAppPackage(APP_PACKAGE)
                    .setAppWaitActivity(APP_WAIT_ACTIVITY)
                    .ignoreHiddenApiPolicyError()
                    .setIgnoreHiddenApiPolicyError(true);

            driver = new AndroidDriver(getAppiumServerUrl(), options);

            System.out.println("[INIT] AndroidDriver successfully initialized.");

        } catch (MalformedURLException e) {
            System.err.println("[ERROR] Invalid Appium server URL: " + e.getMessage());
            throw new RuntimeException("Invalid Appium server URL", e);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize AndroidDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("AndroidDriver initialization failed", e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        if (driver != null) {
            System.out.println("[CLEANUP] Quitting AndroidDriver...");
            driver.quit();
            System.out.println("[CLEANUP] AndroidDriver session ended.");
        }
    }
}
