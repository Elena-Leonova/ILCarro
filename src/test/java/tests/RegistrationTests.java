package tests;

import manager.ProviderData;
import models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTests extends TestBase{
    @BeforeMethod(alwaysRun = true)
    public void precondition(){
        if(app.getUser().isLogged()){
            app.getUser().logout();
        }
    }
    @Test( priority = 1, groups = {"sanityGroup", "regressionGroup"})
    public void registrationPositive(){
        int i =(int)(System.currentTimeMillis()/1000)%3600;

        User user = new User()
                .withName("Sasha")
                .withLastName("Petrov")
                .withEmail("sasha" + i + "@gmail.com")
                .withPassword("Mynameislena1!");
        app.getUser().openRegistrationForm();
        app.getUser().pause(2000);
        app.getUser().fillRegistrationForm(user);
        app.getUser().submitForm();
        app.getUser().pause(2000);
        logger.info("Registration test starts with data : " + user.getEmail()
        + " & " + user.getPassword()
        );
        Assert.assertTrue(app.getUser().isRegistered());
        app.getUser().clickOkButton();


    }

    @Test( priority = 3, dataProvider = "userModelListDTO_CSV", dataProviderClass = ProviderData.class)
    public void registrationPositiveCSV(User user){

        app.getUser().openRegistrationForm();
        app.getUser().pause(2000);
        app.getUser().fillRegistrationForm(user);
        app.getUser().submitForm();
        app.getUser().pause(2000);
        logger.info("Registration test starts with data : " + user.getEmail()
                + " & " + user.getPassword()
        );
        Assert.assertTrue(app.getUser().isRegistered());
        app.getUser().clickOkButton();

    }
    @Test( priority = 2, dataProvider = "userModelListReg", dataProviderClass = ProviderData.class)
    public void registrationUserModelListRegPositive(User user){
        logger.info("User : "
                + user.getName() + " & "
                + user.getLastName() + " & "
                + user.getEmail()+ " & "
                + user.getPassword() + " is provided");
        app.getUser().openRegistrationForm();
        app.getUser().pause(2000);
        app.getUser().fillRegistrationForm(user);
        app.getUser().submitForm();
        app.getUser().pause(2000);
        Assert.assertTrue(app.getUser().isRegistered());
        app.getUser().pause(2000);
        app.getUser().clickOkButton();
    }

    @Test(priority = 5)
    public void registrationNegativeWrongPassword(){
        int i =(int)(System.currentTimeMillis()/1000)%3600;
        User user = new User()
                .withName("Sasha")
                .withLastName("Petrov")
                .withEmail("sasha" + i + "@gmail.com")
                .withPassword("mynameislena");
        app.getUser().openRegistrationForm();
        logger.info("Method openRegistrationForm() invoked ");
        app.getUser().fillRegistrationForm(user);
        app.getUser().pause(1000);
       // app.getUser().click(By.id("email"));
        logger.info("Method fillRegistrationForm() invoked ");
        app.getUser().submitForm();
        logger.info("Method submitForm()invoked ");
        logger.info("Registration test starts with data : " + user.getEmail()
                + " & " + user.getPassword());
        app.getUser().pause(2000);
       // Assert.assertTrue(app.getUser().isElementPresent(By.cssSelector("div[class='ng-star-inserted'][.='Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]']")));
        //Assert.assertTrue(app.getUser().isElementPresent(By.cssSelector("div[class='error']")));
        Assert.assertTrue(app.getUser().isElementPresent(By.xpath("//div[text()='Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]']")));
        app.getUser().pause(1000);
    }
    @Test(priority = 4)
    public void registrationNegativeWrongEmail(){
        int i =(int)(System.currentTimeMillis()/1000)%3600;
        User user = new User()
                .withName("Sasha")
                .withLastName("Petrov")
                .withEmail("sasha" + i + "gmail.com")
                .withPassword("Mynameislena1!");
        app.getUser().openRegistrationForm();
        logger.info("Method openRegistrationForm() invoked ");
        app.getUser().fillRegistrationForm(user);
        logger.info("Method fillRegistrationForm() invoked ");
        app.getUser().submitForm();
        logger.info("Method submitForm()invoked ");
        logger.info("Registration test starts with data : " + user.getEmail()
                + " & " + user.getPassword());
        app.getUser().pause(2000);
        Assert.assertTrue(app.getUser().isElementPresent(By.xpath("//div[.='Wrong email format'][1]")));
    }

   // @AfterMethod(alwaysRun = true)
    public void postConditions(){
        app.getUser().clickOkButton();
    }

}
