package testscripts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;

import org.apache.xerces.impl.xpath.XPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;


public class ReviewLibrary extends DriverScript {

	private static final By LocatorLoginButton = null;

	// Stores current window handle
	public static String currentWindowHandle;

	// Store method return result
	public static String methodReturnResult = null;

	// Site name
	public static String testSiteName = "WalletHub";

	// User name
	public static String userName = null;

	// Expected page titles
	public static String WalletHubNetworkPageTitle = null;
	public static String WalletHubTitle = "Wallet Hub";
	public static String WalletHubPageTitle = null;

	/*
	 * .............. Name of the WebElements present on the WebPage
	 * .................
	 */
	
	public static String nameLoginButton1 = "'Login' button on Wallet login page";
	public static String nameLoginName = "'Email Address' button on WalletHub login page";
	public static String namePassword = "'Password' button on WalletHub login page";
	public static String nameLogIn = "'LogIn' Button";
	public static String nameLogout = "'Logout' Button";
	public static String namePolicyDropDown = "'Policy' Drop Down";
	public static String nameFiveStar = "'5 Star' Review";
	public static String nameHealth = "'Health' Option";
	public static String nameFiveStarHealth = "'Five Star' On Health Page";
	public static String nameYourReview = "'Write Review' On Review Box";
	public static String nameSubmitButton = "'Submit' Button";
	public static String nameFooter = "'Close' Footer";
	public static String nameReviewSuccessful = "'Awesome! Your Test Insurance Company review has been posted.' message";
	public static String nameUserMenu = "'User Menu' Drop Down";
	public static String nameProfile = "'Profile' Button";
	public static String nameReviewFeed = "'Feedback' added";
	public static String nameVerifyReview = "'Review' Verified";
	public static String nameReviewButton = "'Review' Button";
	
	
	/* .............. Locators for the test ................. */
	
	public static By LocatorLoginButton1 = By.xpath("//a[contains(.,'Login')]");
	public static By LocatorLoginName = By.xpath("//*[@placeholder='Email Address']");
	public static By LocatorPassword = By.xpath("//*[@placeholder='Password']");
	public static By LocatorLogInButton = By.xpath(".//span[contains(text(),'Login')]");
	public static By LocatorLogoutButton = By.partialLinkText("Logout"); 
	public static By LocatorPolicyDropDown = By.xpath("//*[@class='bf-icon-down-open']");
	public static By LocatorFiveStar = By.xpath(".//*[@id='overallRating']/a[5]");
	public static By LocatorHealth = By.partialLinkText("Health");
	public static By LocatorFiveStarHealth = By.xpath("//*[@id='overallRating']/a[5]");
	public static By LocatorYourReview = By.xpath("//*[@id='review-content']");
	public static By LocatorSubmitButton = By.xpath("//input[@type='submit']");
	public static By LocatorFooter = By.xpath("//*[@class='af-icon-cross']");
	public static By LocatorReviewSuccessful = By.xpath("//*[contains(text(),'has been posted')]");
	public static By LocatorUserMenu = By.xpath("//*[@class='user']");
	public static By LocatorProfile = By.partialLinkText("Profile"); 
	public static By LocatorReviewFeed = By.xpath("//*[@class='content']");
	public static By LocatorVerifyReview = By.xpath("//*[@class='reviews']");
	public static By LocatorReviewButton = By.xpath("//a[contains(.,'Reviews')]");
	
	// Create a browser instance and navigate to the test site
	public static String navigate() throws MalformedURLException,
	InterruptedException {

		System.out.println("Navigating to the test site - " + testSiteName
				+ " ...");
		APPLICATION_LOGS.debug("Navigating to the test site - " + testSiteName
				+ " ...");

		// Open a driver instance if not opened already

		try {

			if (wbdv == null) {

				if (CONFIG.getProperty("is_remote").equals("true")) {

					// Generate Remote address
					String remote_address = "http://"
							+ CONFIG.getProperty("remote_ip") + ":4444/wd/hub";
					remote_url = new URL(remote_address);

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						dc = DesiredCapabilities.internetExplorer();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						dc = DesiredCapabilities.firefox();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome"))
						dc = DesiredCapabilities.chrome();

					// Initiate Remote Webdriver instance
					wbdv = new RemoteWebDriver(remote_url, dc);

				}

				else {

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						wbdv = new InternetExplorerDriver();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						wbdv = new FirefoxDriver();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome"))
						wbdv = new ChromeDriver();

				}

			}

		}

		catch (Throwable initException) {

			APPLICATION_LOGS.debug("Error came while initiating driver : "
					+ initException.getMessage());
			System.err.println("Error came while initiating driver : "
					+ initException.getMessage());

		}

		// Initiate Event Firing Web Driver instance
		driver = new EventFiringWebDriver(wbdv);

		// Implicitly wait for 30 seconds for browser to open
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Delete all browser cookies
		driver.manage().deleteAllCookies();

		// Navigate to facebook application
		driver.navigate().to(CONFIG.getProperty("test_site_url"));

		// Maximize browser window
		driver.manage().window().maximize();

		APPLICATION_LOGS.debug("Navigated to the test site - " + testSiteName);
		System.out.println("Navigated to the test site - " + testSiteName);

		return "Pass : Navigated to the test site - " + testSiteName;

	}

	// Retrive Login data to the WalletHub application
	public static String login(int Data_Row_No) throws InterruptedException,
	IOException {

		
		APPLICATION_LOGS.debug("Click on Login button");
		
		FunctionLibrary.waitForPageToLoad();

		FunctionLibrary.waitForElementToLoad(LocatorLoginButton1);

		methodReturnResult = FunctionLibrary.clickAndWait(LocatorLoginButton1, nameLoginButton1);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		}
		
		APPLICATION_LOGS.debug("Logging in to the test site - " + testSiteName);
		System.out.println("Logging in to the test site - " + testSiteName);

		String userName = null;
		String password = null;

		try {

			userName = testData.getCellData("Login", "UserId_In", Data_Row_No);

			password = testData
					.getCellData("Login", "Password_In", Data_Row_No);

			APPLICATION_LOGS
			.debug("Successfully Retrieved data from Xls File :-  Username : "
					+ userName + " and Password : " + password);
			System.out
			.println("Successfully Retrieved data from Xls File :-  Username : "
					+ userName + " and Password : " + password);

		}

		catch (Exception e) {

			APPLICATION_LOGS.debug("Error while retrieving data from xls file"
					+ e.getMessage());
			System.out.println("Error while retrieving data from xls file"
					+ userName);
			return "Fail : Error while retrieving data from xls file";

		}

		// Verify whether Username input-box, Password input-box and SignIn
		// button present on the page or not
		Boolean usernameFieldPresent = FunctionLibrary.isElementPresent(
				LocatorLoginName, nameLoginName);
		Boolean passwdFieldPresent = FunctionLibrary.isElementPresent(
				LocatorPassword, namePassword);
		Boolean signInButtonPresent = FunctionLibrary.isElementPresent(
				LocatorLogInButton, nameLogIn);

		if (!usernameFieldPresent && !passwdFieldPresent
				&& !signInButtonPresent) {
			return "Fail : Username Field or Password Field or LogIn button is not present on the page ";
		}

		// Clear Username input-box and input username
		FunctionLibrary.clearField(LocatorLoginName,
				nameLoginName);
		FunctionLibrary.input(LocatorLoginName, nameLoginName,
				userName);

		// Clear Password input-box and input password
		FunctionLibrary.clearField(LocatorPassword,
				namePassword);
		FunctionLibrary.input(LocatorPassword, namePassword,
				password);

		// Click on the Log in button
		FunctionLibrary.clickAndWait(LocatorLogInButton, nameLogIn);

		APPLICATION_LOGS.debug("Successfully logged in to the test site - "
				+ testSiteName);
		System.out.println("Successfully logged in to the test site - "
				+ testSiteName);

		return "Pass : Logged in to the test site - " + testSiteName;

	}

	// Navigate and login to WalletHub
	public static String navigateAndLoginToWalletHub() throws InterruptedException,
	IOException, BiffException {

		// Navigate to WalletHub
		methodReturnResult = ReviewLibrary.navigate();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Login to WalletHub
		methodReturnResult = ReviewLibrary.login(1);
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		return "Successfully launched to WalletHub";
	}
	
	// Hover and click on 5 stars
	public static String hoverStar() throws InterruptedException,
	IOException, BiffException {
	
		APPLICATION_LOGS.debug("Clicking on 5 stars in network page");
		System.out.println("Clicking on 5 stars in network page");
		
		FunctionLibrary.waitForElementToLoad(LocatorFooter);
		FunctionLibrary.clickAndWait(LocatorFooter, nameFooter);
        Thread.sleep(5000);		
	
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector("div.wh-rating-notes"))).perform();

        // click specified star
        driver.findElement(By.xpath("//*[@class='hover'][contains(text(),'5')]")).click();
		
		return "Pass : Clicked on 5 stars";
	}
	
	// Logout from the application
	public static String logout() throws InterruptedException {

		APPLICATION_LOGS
		.debug("Logging out of the test site - " + testSiteName);
		System.out.println("Logging out of the test site - " + testSiteName);

		new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@class='user']"))).perform();
		
		FunctionLibrary.waitForElementToLoad(LocatorLogoutButton);
		
		FunctionLibrary.clickAndWait(LocatorLogoutButton, nameLogout);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		}

		// Delete all cookies
		try {
			driver.manage().deleteAllCookies();
		}

		catch (Throwable deleteCookieException) {

			APPLICATION_LOGS.debug("Error came while clearing cookies : "
					+ deleteCookieException.getMessage());
			System.err.println("Error came while clearing cookies : "
					+ deleteCookieException.getMessage());

		}
		return "Logout from application successfully";
		
	}

	
	/* Selecting health from policy drop down */
	
	public static String selectHealthFromPolicyDropdown()
			throws InterruptedException, IOException, BiffException {

		APPLICATION_LOGS.debug("Selecting Health from Policy drop-down ...");

		FunctionLibrary.waitForElementToLoad(LocatorPolicyDropDown);

		methodReturnResult = FunctionLibrary.clickAndWait(LocatorPolicyDropDown, namePolicyDropDown);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		}

		FunctionLibrary.waitForElementToLoad(LocatorHealth);

		methodReturnResult = FunctionLibrary.clickAndWait(LocatorHealth, nameHealth);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		} 
		
		return "Clicked on health option successfully";
		
	}

	// Click on 5 stars in Policy page
		public static String fiveStar()
			throws InterruptedException, IOException, BiffException {
		
			FunctionLibrary.clickAndWait(LocatorFiveStarHealth, nameFiveStarHealth);

			APPLICATION_LOGS.debug("Successfully clicked on 5 stars");
			System.out.println("Successfully clicked on 5 stars");

			return "Pass : Successfully clicked on 5 stars";
    }
	
	// Write a Review
		public static String writeReview()
				throws InterruptedException, IOException, BiffException {
			
			APPLICATION_LOGS.debug("verifying the Facebook home page ");
			System.out.println("verifying the Facebook home page");
			
			FunctionLibrary.waitForElementToLoad(LocatorYourReview);
			
	        FunctionLibrary.clickAndWait(LocatorYourReview, nameYourReview);
	            
			FunctionLibrary.clearAndInput(LocatorYourReview, nameYourReview, Keys.chord
		    ("Hi, I'm an Automation Engineer and this is review I'm writing using automation script. Isn't it amazing  because I don't have to put any effort by myself and my script is so powerful that it is doing the work for me. ")); 
			
			FunctionLibrary.clickAndWait(LocatorSubmitButton, nameSubmitButton);
	    
			return "Pass : wrote a review of 200+ words";
		}
		
		// Review feed
			public static String reviewFeed()
			throws InterruptedException, IOException, BiffException {
					
			APPLICATION_LOGS.debug("Verifying review");
			System.out.println("Verifying review");

			Boolean expectedMessage = FunctionLibrary.isElementPresent(LocatorReviewSuccessful, nameReviewSuccessful);
				
				if(expectedMessage==true){
					System.out.println("Pass");
					return "Message posted successfully";
				}
				
			return "Else Fails";
		
				}
			
			public static String reviewFeedProfile()
					throws InterruptedException, IOException, BiffException {
					
			APPLICATION_LOGS.debug("Verifying review on user profile");
			System.out.println("Verifying review on user profile");

			new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@class='user']"))).perform();
					
			FunctionLibrary.waitForElementToLoad(LocatorProfile);
					
			FunctionLibrary.clickAndWait(LocatorProfile, nameProfile);
			 	if (methodReturnResult.contains(failTest)) {
				return methodReturnResult;
				}
					
			Boolean expectedMessage = FunctionLibrary.isElementPresent(LocatorReviewFeed, nameReviewFeed);
					
			if(expectedMessage==true){
			System.out.println("Pass");
			return "Review got posted successfully in user's profile";
				}
					
			return "Else failed to post review in user's profile";
					
				}
				
			public static String verifyFeed()
					throws InterruptedException, IOException, BiffException {
					
			APPLICATION_LOGS.debug("Verifying review review page of user");
			System.out.println("Verifying review review page of user");

			FunctionLibrary.waitForElementToLoad(LocatorReviewButton);
					
			FunctionLibrary.clickAndWait(LocatorReviewButton, nameReviewButton);
			if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
				}
					
			Boolean expectedMessage = FunctionLibrary.isElementPresent(LocatorVerifyReview, nameVerifyReview);
					
			if(expectedMessage==true){
			System.out.println("Pass");
			return "Review got posted successfully in user's profile";
				}
					
			return "Else failed to post review in user's profile";
				
				}
		
		}


