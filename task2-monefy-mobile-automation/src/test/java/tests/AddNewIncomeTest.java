package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.IncomePage;
import pages.OnboardingPage;

import java.math.BigDecimal;

@Epic("Monefy App Testing")
@Feature("Income Management")
@Story("Add New Income Transaction")
public class AddNewIncomeTest extends BaseTest {

    private HomePage homePage;
    private IncomePage incomePage;
    private OnboardingPage onboardingPage;

    private static final BigDecimal INCOME_AMOUNT = BigDecimal.valueOf(3000.78);

    @BeforeClass
    public void initializePages() {
        System.out.println("[Init] Initializing page objects...");
        onboardingPage = new OnboardingPage(driver);
        homePage = new HomePage(driver);
        incomePage = new IncomePage(driver);
    }

    @BeforeMethod
    public void completeOnboardingIfNeeded() {
        System.out.println("[Setup] Completing onboarding flow...");
        onboardingPage.completeInitialAppLaunchFlow();
    }

    @Test(description = "Verify income is added and balance updates correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Adds an income of 3000 under 'Salary' and verifies balance is updated.")
    public void addIncomeAndValidateBalance() {
        System.out.println("[Test] Starting addIncomeAndValidateBalance");

        Allure.step("Get starting balance from Home Page");
        BigDecimal startingBalance = homePage.getBalanceAmount().orElseThrow(() ->
            new AssertionError("[Assert] Starting balance is missing")
        );
        System.out.println("[Info] Starting balance: " + startingBalance);

        Allure.step("Click 'Add Income' button");
        homePage.clickAddIncome();

        Allure.step("Enter income amount: " + INCOME_AMOUNT);
        incomePage.enterAmount(String.valueOf(INCOME_AMOUNT));

        Allure.step("Confirm the income transaction");
        incomePage.confirmIncome();

        Allure.step("Select 'Salary' category");
        incomePage.selectSalaryCategory();

        Allure.step("Get updated balance and assert it reflects the added income");
        BigDecimal updatedBalance = homePage.getBalanceAmount().orElseThrow(() ->
            new AssertionError("[Assert] Updated balance is missing")
        );
        System.out.println("[Assert] Updated balance: " + updatedBalance);

        Assert.assertEquals(updatedBalance, startingBalance.add(INCOME_AMOUNT),
            "Balance mismatch: Income may not have been recorded correctly.");

        System.out.println("[Pass] addIncomeAndValidateBalance completed successfully");
    }
}
