package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsPage;
import pages.HomePage;
import pages.OnboardingPage;
import pages.SettingsPage;
import org.testng.Assert;

@Epic("Monefy App Testing")
@Feature("Account Management")
@Story("Adding New Account")
public class AddNewAccountTest extends BaseTest {
    private OnboardingPage onboardingPage;
    private SettingsPage settingsPage;
    private AccountsPage accountsPage;

    @BeforeClass
    public void initializePageObjects() {
        System.out.println("[Init] Initializing page objects...");
        onboardingPage = new OnboardingPage(driver);
        settingsPage = new SettingsPage(driver);
        accountsPage = new AccountsPage(driver);
    }

    @BeforeMethod
    public void completeOnboardingIfNeeded() {
        onboardingPage.completeInitialAppLaunchFlow();
    }

    @Test(description = "Verify a new account can be added successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add a new account and verify that it appears in the account list.")
    public void addAccountAndValidatePresence() {
        String accountName = "Test Acc " + System.currentTimeMillis();
        String initialAmount = "3000";

        System.out.println("[Test] Starting addAccountAndValidatePresence with account: " + accountName);

        Allure.step("Open settings menu");
        settingsPage.openAccountsSection();

        Allure.step("Tap 'Add Account'");
        accountsPage.tapAddAccount();

        Allure.step("Enter account name: " + accountName);
        accountsPage.enterAccountName(accountName, "Account Name");

        Allure.step("Set initial balance: " + initialAmount);
        accountsPage.setInitialAmount(initialAmount, "Initial Amount");

        Allure.step("Select an account icon");
        accountsPage.selectAccountTypeIcon();

        Allure.step("Save new account");
        accountsPage.saveAccount();

        Allure.step("Re-open settings menu to verify account list");
        settingsPage.openAccountsSection();

        Allure.step("Verify account '" + accountName + "' is present");
        boolean accountExists = accountsPage.isAccountPresent(accountName);
        Assert.assertTrue(accountExists, "Account '" + accountName + "' was not found after saving.");

        System.out.println("[Pass] addAccountAndValidatePresence completed successfully");
    }
}
