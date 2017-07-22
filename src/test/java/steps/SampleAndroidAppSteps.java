package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SampleAndroidAppSteps {

    private AppiumDriver driver;
    private URL remoteUrl;
    HashMap hm;

    public SampleAndroidAppSteps(){
        hm = new HashMap();
    }

    @Before
    public void setUp() throws MalformedURLException {
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @When("^I launch the test app$")
    public void i_launch_the_test_app() throws Throwable {
        driver=CommonConfiguration.getDriver(CommonConfiguration.getDesiredCapabilities("Android","7.1.1",null,hm));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        MobileElement el1 = (MobileElement) driver.findElementById("sign_in_button");
        Assert.assertTrue(el1.isDisplayed());
    }

    @And("^I choose the google login$")
    public void i_choose_the_google_login() throws Throwable {
       // MobileElement el1 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button[1]");
        MobileElement el1 = (MobileElement) driver.findElementById("sign_in_button");
        el1.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MobileElement el2 = (MobileElement) driver.findElementById("com.google.android.gms:id/account_display_name");
        Assert.assertTrue(el2.isDisplayed());
        Assert.assertTrue(el2.getText().contains("normal"));

    }

    @Then("^I see account picker with my email \"([^\"]*)\"$")
    public void i_see_account_picker_with_my_email(String arg1) throws Throwable {
        MobileElement el1 = (MobileElement) driver.findElementById("com.google.android.gms:id/account_name");
        System.out.println("the argument passed was "+arg1);
        Assert.assertTrue(el1.getText().contains(arg1));
    }
}
