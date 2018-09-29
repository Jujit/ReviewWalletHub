package testcases;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;

import testscripts.DriverScript;
import testscripts.FunctionLibrary;
import testscripts.ReviewLibrary;

/**
 * @author Jujit
 */

public class verifyReviewWalletHub extends DriverScript {

	/* .............. Name of the WebElements present on the WebPage ................. */

	public static String nameLoginButton = "'Login' button on Wallet login page";
	public static String nameLoginName = "'Email' button on Wallet login page";
	public static String namePassword = "'Password' button on Wallet login page";
	public static String nameLogIn = "'LogIn' Button";
	public static String nameLogout = "'Logout' Button";
	public static String namePolicyDropDown = "'Policy' Drop Down";

    /* .............. Locators for the test ................. */
	
	public static By LocatorLoginButton = By.xpath("//a[contains(.,'Login')]");
	public static By LocatorLoginName = By.xpath("//*[@placeholder='Email Address']");
	public static By LocatorPassword = By.id("//*[@placeholder='Password']");
	public static By LocatorLogInButton = By.id("//*[@class='btn blue touch-element-cl']");
	public static By LocatorLogoutButton = By.id("u_o_b");   
	public static By LocatorPolicyDropDown = By.xpath("//*[@id='reviewform']/div[1]/div/div");
	
			
		// Navigate to test insurance company page
	      public static String verifyReview() throws InterruptedException,
	      IOException, BiffException {

		APPLICATION_LOGS
		.debug("Executing test case : Navigate to WalletHub page");

		System.out.println("Executing test case : Navigate to WalletHub page");
		
		

		// Navigate and login to WalletHub
		methodReturnResult = ReviewLibrary.navigateAndLoginToWalletHub();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Rate WalletHub with stars
		methodReturnResult = ReviewLibrary.hoverStar();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Click on health from Policy dropdown 
		methodReturnResult = ReviewLibrary.selectHealthFromPolicyDropdown();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Click on 5 stars
		methodReturnResult = ReviewLibrary.fiveStar();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Write a review
		methodReturnResult = ReviewLibrary.writeReview();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		//Review Feed posted there
		methodReturnResult = ReviewLibrary.reviewFeed();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		//Review Feed in profile
		methodReturnResult = ReviewLibrary.reviewFeedProfile();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Verify feed in review
		methodReturnResult = ReviewLibrary.verifyFeed();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Logout from WalletHub
		methodReturnResult = ReviewLibrary.logout();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		return "Pass : Successfully reviewed WalletHub";
	      
	   }
}


