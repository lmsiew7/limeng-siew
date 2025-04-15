package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpensePage;
import pages.HomePage;
import pages.OnboardingPage;

import java.math.BigDecimal;

@Epic("Monefy App Testing")
@Feature("Expense Management")
@Story("Adding New Expenses")
public class AddNewExpenseTest extends BaseTest {

    private HomePage homePage;
    private ExpensePage expensePage;
    private OnboardingPage onboardingPage;

    private static final BigDecimal EXPENSE_AMOUNT = BigDecimal.valueOf(1600.99);

    @BeforeClass
    public void initializePages() {
        System.out.println("[Init] Instantiating page objects...");
        onboardingPage = new OnboardingPage(driver);
        homePage = new HomePage(driver);
        expensePage = new ExpensePage(driver);
    }

    @BeforeMethod
    public void completeOnboarding() {
        onboardingPage.completeInitialAppLaunchFlow();
    }

    @Test(description = "Verify that adding expense by tapping a category icon updates the balance")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Adds an expense by selecting the 'House' category icon and checks the balance is updated accordingly.")
    public void addExpenseFromCategoryIcon() {
        System.out.println("[Test] addExpenseViaCategoryIcon_shouldUpdateBalance started");

        Allure.step("Click on the 'House' category icon");
        homePage.clickHouseCategory();

        Allure.step("Enter amount '" + EXPENSE_AMOUNT + "' using keypad");
        expensePage.enterAmount(String.valueOf(EXPENSE_AMOUNT));

        Allure.step("Confirm the expense");
        expensePage.confirmExpense();

        Allure.step("Verify updated balance on Home Page");
        BigDecimal balance = homePage.getBalanceAmount().orElseThrow(() ->
                new AssertionError("[Assert] Balance is missing")
        );
        System.out.println("[ASSERT] Actual balance: " + balance);

        Assert.assertEquals(balance, EXPENSE_AMOUNT.negate(), "Balance mismatch: Expense may not have been recorded correctly.");

        System.out.println("[PASS] addExpenseFromCategoryIcon completed");
    }
}
