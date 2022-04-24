package qa.guru.tests;

import org.junit.jupiter.api.*;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static qa.guru.utils.RandomUtils.*;

public class RegistrationFormWithRandomUtilsTests {
    String firstName = getRandomString(10),
            lastName = getRandomString(10),
            email = getRandomEmail(),
            gender = "Male",
            phoneNumber = "0000000000",
            year = "2000",
            month = "May",
            day = "17", //в формате mm
            subject = "Maths",
            address = "somestreet 1",
            hobby = "Sports",
            imgName = "img.png",
            imgPath = "img/" + imgName,
            state = "NCR",
            city = "Delhi";

    String expectedFullName = format("%s %s", firstName, lastName);
    String expectedDate = format("%s %s,%s",day,month,year);

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @AfterAll
    static void setDown() {
        Configuration.holdBrowserOpen = false;
    }

    @Test
    void formTests() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__day--0"+day+":not(react-datepicker__day--outside-month)").click();
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#currentAddress").setValue(address);
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFromClasspath(imgPath);
        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText(city)).click();
        $("#submit").click();

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".modal-body").shouldHave(

                text(expectedFullName),
                text(email),
                text(gender),
                text(phoneNumber),
                text(expectedDate),
                text(subject),
                text(hobby),
                text(imgName),
                text(address),
                text(state + " " + city)
        );
    }
}
