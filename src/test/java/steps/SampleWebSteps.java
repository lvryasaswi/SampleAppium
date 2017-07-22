package steps;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SampleWebSteps {

    URL url;
    AppiumDriver appDriver;
    HashMap hm;

    public SampleWebSteps(){
        hm = new HashMap();
    }

    @Before
    public void startServer(){
        CommonConfiguration.startServer();
    }
    @After
    public void stopServer(){
        CommonConfiguration.stopServer();
    }

    @When("^I goto quikr in mobile web broswer$")
    public void i_goto_quikr_in_mobile_web_broswer() throws Throwable {
        appDriver=CommonConfiguration.getDriver(CommonConfiguration.
                getDesiredCapabilities("Android","7.1.1","Chrome",hm));

        appDriver.get("http://m.quikr.com");
        appDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        MobileElement ele= (MobileElement) appDriver.findElementById("hamburger");
        Assert.assertTrue(ele.isDisplayed());
    }

    @When("^I choose the to register$")
    public void i_choose_the_facebook_login_to_register() throws Throwable {
        MobileElement ele= (MobileElement) appDriver.findElementById("hamburger");
        ele.click();
        ele=(MobileElement)appDriver.findElementByPartialLinkText("Account");
        ele.click();
        appDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ele=(MobileElement) appDriver.
                findElementByXPath("//android.widget.Button[@text='facebook']");
        Assert.assertTrue(ele.isDisplayed());
    }

    @Then("^I select the facebook option$")
    public void i_see_a_facebook_option() throws Throwable {
        MobileElement ele=(MobileElement) appDriver.
                findElementByXPath("//*[@id=\"pageLogin\"]/div/div/div/div[1]/div[3]/div[2]/button[2]");
        ele.click();

        appDriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
        String currUrl=appDriver.getCurrentUrl();
        System.out.print("current url is "+currUrl);
        Assert.assertTrue(currUrl.contains("facebook.com"));

    }
}
