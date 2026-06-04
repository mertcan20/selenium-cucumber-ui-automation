package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ElementUtils {

    private ElementUtils() {
    }

    public static List<String> convertElementsToTextList(List<WebElement> elements) {
        List<String> elementTexts = new ArrayList<>();

        for (WebElement element : elements) {
            if (!element.getText().isEmpty()) {
                elementTexts.add(element.getText());
            }
        }

        return elementTexts;
    }

    public static List<String> getElementsText(By locator) {
        List<WebElement> elements = Driver.getDriver().findElements(locator);
        return convertElementsToTextList(elements);
    }

    public static void clickWithRetry(WebElement element, int timeoutInSeconds) {
        for (int i = 0; i < timeoutInSeconds; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                WaitUtils.waitForSeconds(1);
            }
        }

        throw new WebDriverException("Element could not be clicked within timeout.");
    }
}
