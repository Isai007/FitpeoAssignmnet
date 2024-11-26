package com.test.fitpeo;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.fitpeo.Base;



public class test_Cases extends Base {
	  @BeforeClass
	    public void startup() {
	        setup();
	        
	    }
	
	    @AfterClass
	    public void quit() {
	        driver.quit();
	    }
	    
	    
	    //Method for Page Scrolling(Horizontal Scroll of 350 pxls)
	    private void scrollPage() {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,350)", "");
	    }
	    //Slider Adjusment(Adjusting the slider to the desired value (820))
	    private void adjustSliderToValue() {
	    	WebElement slider=driver.findElement(By.xpath("//input[@data-index='0']"));
	    	Actions actions = new Actions(driver);
	        actions.clickAndHold(slider).perform();     //Clicking and holding the slide bar to perform the sliding
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,350)", "");
	        //Loop to adjust the slider to the desired value 
	        for (int i = 0; i < 2000; i++) {                       
	            actions.sendKeys(Keys.ARROW_UP).perform();  
	            WebElement input = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']"));
	            String inputdata= input.getAttribute("value");  //Getting the Value of slider
	            if(inputdata.equalsIgnoreCase("820")) {      //Managed condition to break the loop once expected value gets selected
	        	  break; //Break the loop once desired value reached
	            }else {
	        	  continue;
	            }
	         }
	        actions.release().perform();  //release the clickAndHold Action
	    }
	    // Updating the input field
	    private void updateTextFieldValue() {
	    	 WebElement input = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']"));
	         input.click(); //Clicking on the text field associated with the slider.
	         //clearing the input field(Used looping to clear the input fields)
	         for(int i=4; i>=0;i--) {
	        	 Actions actions = new Actions(driver);
	         	actions.sendKeys(Keys.BACK_SPACE).perform();
	         }
	         input.sendKeys("560");// Passing the value to input field
	    }
	    //Validating the slider and input values 
	    private void validateSliderValue() {
	        WebElement slider = driver.findElement(By.xpath("//input[@data-index='0']"));
	        String sliderValue = slider.getAttribute("value");
	        Assert.assertEquals(sliderValue, "560", "Slider value does not match the input field value.");
	    }
	    //Selecting the required code checkboxes
	    private void selectCPTCodes() {
	    	scrollPage();  // Scroll down to codes section      
	        List<String> codesToCheck = List.of("CPT-99091", "CPT-99453", "CPT-99454","CPT-99474");//Added all the required codes in the list
	        List<WebElement> codes = driver.findElements(By.xpath("//*[@class='MuiTypography-root MuiTypography-body1 inter css-1s3unkt']"));
	        for (int i = 0; i < codes.size(); i++) {
	            String codeName = codes.get(i).getText();
	             // Check if the code name is in the list of CPT codes to select
	             if (codesToCheck.contains(codeName)) {
	              WebElement checkbox = driver.findElement(By.xpath("(//label[@class='MuiFormControlLabel-root MuiFormControlLabel-labelPlacementEnd inter css-1ml0yeg'])[" + (i + 1) + "]"));
	             checkbox.click(); // Find and click the corresponding checkbox
	             }
	        }
	    }
	    //Validating total reimbursemnet
	    private void validateTotalRecurringReimbursement() {
	    	List<WebElement> totalValues=driver.findElements(By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 inter css-1xroguk']"));
            //Used loop to get all the total pricing details
            for(int i=0; i<totalValues.size();i++) {
        	String valueText=totalValues.get(i).getText();
              	//Getting Total Recurring Reimbursement for all Patients Per Month details
        	    if(valueText.contains("Total Recurring Reimbursement")) {
        		WebElement price=driver.findElement(By.xpath("(//p[@class='MuiTypography-root MuiTypography-body1 inter css-1bl0tdj'])["+(i+1)+"]"));
        		String TotalPricee=price.getText(); //Getting the total revenue value 
        		//Validating total Amount
        		try {
        		Assert.assertEquals(TotalPricee, "$110700");
        		}catch(AssertionError e) {
        			throw e;
        		    }
        		 }
          }
	    }
	    
	    private void revenuePage() {
	    	WebElement calculator=driver.findElement(By.xpath("//*[contains(@class,'satoshi MuiBox-root css-5ty6tm') and text()='Revenue Calculator']"));
			calculator.click();
	    }
	    
	    @Test
	    public void test() {
	        // Step 1: Navigate to the FitPeo Home page (URL handled in Base class)
	
	        // Step 2: Navigate to the Revenue Calculator Page
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			revenuePage();
	
	        // Step 3: Scroll down to the slider
	        scrollPage();
	
	        // Step 4: Adjust the slider to the desired value (820)
	        adjustSliderToValue();
	
	        // Step 5: Update the Text Field to a new value (560)
	        updateTextFieldValue();
	
	        // Step 6: Validate the Slider value matches the input value
	        validateSliderValue();
	
	        // Step 7: Select CPT codes
	        selectCPTCodes();
	
	        // Step 8: Validate Total Recurring Reimbursement(If we continuing the script with the slider value of 560, then Assertion gets failed and validation should be succeed when we continuing the script with the slider value of 820)
	        validateTotalRecurringReimbursement();
	    }
	    
}