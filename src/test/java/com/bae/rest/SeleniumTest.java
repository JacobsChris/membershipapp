package com.bae.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class SeleniumTest {
    /*-
    TODO:
        1.  Test that each group can be accessed and returned from
        2.  Test that member can be added to a group
        3.  Test that above member can edited
        4.  During above, ensure that name constraints are adhered to
        5.  Test that above member can be deleted
     */

    @LocalServerPort
    private int port;
    private WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAccessGroup() {

        driver.get("http://localhost:63342/membershipapp/static/index.html?_ijt=rdf191mtdj5rjfsfu3t37dko32");

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]")).click();

        driver.findElement(By.id("goBackButton")).click();

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]")).click();

        driver.findElement(By.id("goBackButton")).click();

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]")).click();

    }

    @Test
    public void testMemberFrontEndCRUD() {

        driver.get("http://localhost:63342/membershipapp/static/index.html?_ijt=rdf191mtdj5rjfsfu3t37dko32");

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]")).click();

        driver.findElement(By.id("addMemberButton")).click(); //will add member

        String shiftHome = Keys.chord(Keys.SHIFT, Keys.HOME);

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[1]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[1]")).sendKeys(shiftHome, Keys.BACK_SPACE, "TestName", Keys.ENTER);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[1]")).sendKeys(shiftHome, Keys.BACK_SPACE, "TestName", Keys.ENTER);
        // above edits member

        driver.findElement(By.id("deleteMemberButton")).click();
        //clicks delete member.  Then add text to alert forms and click ok etc


    }


    @After
    public void tearDown() throws Exception {
        this.driver.close();
    }
}
