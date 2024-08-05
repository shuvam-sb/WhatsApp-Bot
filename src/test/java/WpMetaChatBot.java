import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WpMetaChatBot {
	private static String browser = "edge";
	private static String url = "https://web.whatsapp.com/";
	private static WebDriver driver;
	@BeforeMethod
	public void startUp() throws Exception {
		if(browser.equals("firefox")) driver = new FirefoxDriver();
		else if(browser.equals("chrome")) driver =new ChromeDriver();
		else if(browser.equals("edge")) driver = new EdgeDriver();
		else {
			throw new Exception("Browser not compartable");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(15000);
		
	}
	@Test
	public void mainTest() throws Exception{
		Thread.sleep(5000);
		WebDriverWait w  =  new WebDriverWait(driver, Duration.ofSeconds(200));
		Actions a = new Actions(driver);
		w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@aria-label,'unread message')]/../../../../../div[1]/div[1]")));
		WebElement unreadChat = driver.findElement(By.xpath("//*[contains(@aria-label,'unread message')]/../../../../../div[1]/div[1]"));
		a.moveToElement(unreadChat).build().perform();
		
		String title = driver.findElement(By.xpath("//*[contains(@aria-label,'unread message')]/../../../../../div[1]/div[1]/div[1]/span[1]")).getAttribute("title");
		Thread.sleep(2000);
		a.moveToElement(unreadChat).click().build().perform();
		
		//Fetching the recent msg from the particular user
		String message = driver.findElement(By.xpath("//div[@id='main']//div[@role='row'][last()]/div/div/div/div/div/div/div/div/span[1]/span[1]")).getText();
		System.out.println("Message = " + message);
		
		//meta icon
		driver.findElement(By.xpath("//img[@class='_ao3e']")).click();
		
		//placeholder text of meta
		WebElement sendMsg = driver.findElement(By.xpath("(//p[@class='selectable-text copyable-text x15bjb6t x1n2onr6'])[2]"));
		//click on text box
		a.moveToElement(sendMsg).click().build().perform();
		//Thread.sleep(2000);
		sendMsg.sendKeys("Generate a reply for this message : " + message);
		driver.findElement(By.xpath("//span[@data-icon='send']")).click();
		Thread.sleep(9000);
		
		//Fetching recent text from meta
		String replyMessage = driver.findElement(By.xpath("//div[@id='main']//div[@role='row'][last()]/div/div/div/div/div/div/div/div/span[1]/span[1]")).getText();
		Actions action = new Actions(driver);
		Thread.sleep(2000); //  hidden animation//loading time
		
		//Move to that particular user
		action.moveToElement(driver.findElement(By.xpath("//*[@title='"+title+"']"))).click().build().perform();
		WebElement replyMsg = driver.findElement(By.xpath("(//p[@class='selectable-text copyable-text x15bjb6t x1n2onr6'])[2]"));
		replyMsg.sendKeys(replyMessage);
		driver.findElement(By.xpath("//span[@data-icon='send']")).click();
		
		//Move back to meta so it can navigate to unread message
		//action.moveToElement(driver.findElement(By.xpath("//*[@title='Myself']"))).click().perform();
		action.moveToElement(driver.findElement(By.xpath("//img[@class='_ao3e']"))).click().build().perform();		
		mainTest(); // make it recursive : BOT
	}
}