import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WpStatus {
	private static WebDriver driver;
	//Hard-Coded Person name (Whose status will be opened)
	public static List<String> viewStatusList = Arrays.asList("Saif (IBM)", "Nithish (IBM)","Lohith(Ibm)");
	public static WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(45));

	
	@Test
	public void viewStatus() throws InterruptedException {
		driver = new EdgeDriver();
		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize();
		Thread.sleep(20000);
		driver.findElement(By.xpath("(//span[contains(@data-icon,'status')])[1]")).click(); // click status
		Thread.sleep(2000);
		Actions action = new Actions(driver);

		for (String vs : viewStatusList) {
			System.out.println(vs);
			try {
				WebElement personStatus = driver.findElement(By.xpath("(//div[@role='list']//span[@title='" + vs + "'])[1]"));
				action.moveToElement(personStatus).click().build().perform();
				// wait for status reply text to hide after viewing to proceed further
				w.until(ExpectedConditions.invisibilityOf(driver
						.findElement(By.xpath("(//p[@class='selectable-text copyable-text x15bjb6t x1n2onr6'])[1]"))));
		
				// p[@class='selectable-text copyable-text x15bjb6t x1n2onr6'])[1]
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(vs + " The person haven't uploaded any status");
		
			}
		}
		driver.quit();
	}
}
