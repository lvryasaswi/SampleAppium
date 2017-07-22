package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonConfiguration {

    private static AppiumDriverLocalService  appService;
    public static String OS_platform=null;

    public static void startServer(){
        appService= AppiumDriverLocalService.buildDefaultService();

/*        appService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("PATH TO NODE"))
                .withAppiumJS(new File("PATH TO APPIUM"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_TIMESTAMP)
                .withLogFile(new File("ANY CUSTOME LOG FILE LOCATION")));*/

        appService.start();

    }

    public static void stopServer(){
        appService.stop();
    }

    public static URL getURL(){
        URL url=null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Return the driver based on the devicetype passed to the getDesiredCapabilities() function.
     * @param dc: from getDesiredCapabilities()
     * @return
     */
    public static AppiumDriver getDriver(DesiredCapabilities dc){
        if(OS_platform.equals("Android")){
            return (new AndroidDriver(CommonConfiguration.getURL(),dc));
        }else{
            return (new IOSDriver(CommonConfiguration.getURL(),dc));
        }
    }
    /**
     * A static method to create a Desired Capabilities.
     * @param deviceType: valid values are Android and IOS
     * @param platformVersion: target device platform version
     * @param browserType: expects the browser type (chrome, firefox, Safari, browser) as valid it.
     * @param hm: any other DesiredCapabilities value should be passed as K,V to the method.
     * @return returns DesiredCapabilities object.
     */
    public static DesiredCapabilities getDesiredCapabilities (String deviceType,
                                                                    String platformVersion,
                                                                    String browserType,
                                                                    HashMap<String, String> hm
                                                                    ){

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        OS_platform=deviceType;

        //Mandatory fields
        desiredCapabilities.setCapability("app", "/home/segfault/workspace/SampleAppium/app/quikr.apk");
        desiredCapabilities.setCapability("platformVersion", platformVersion);
        desiredCapabilities.setCapability("platformName",deviceType);
        desiredCapabilities.setCapability("deviceName",deviceType);

        //Setting the browser type
        if(browserType!=null) {
            desiredCapabilities.setCapability("browserName", browserType);
        }

        // Any other values should be passed as HashMap.
        if(deviceType.equals("Android")) {
            if (hm.entrySet().size() > 0) {
                // Get an iterator
                Iterator i = hm.entrySet().iterator();

                // Display elements
                while(i.hasNext()) {
                    Map.Entry me = (Map.Entry)i.next();
                    desiredCapabilities.setCapability((String) me.getKey(),(String)me.getValue());
                }
            }
        }

        return desiredCapabilities;
    }

    /**
     * Explicit wait by using ID locator
     * @param driver : the current driver being used
     * @param locatorValue The actual id of the element that we are waiting for.
     * @param time
     */
    public void explicitWaitById(AppiumDriver driver, String locatorValue, int time){
        WebDriverWait wait=new WebDriverWait(driver,time);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
    }

}
