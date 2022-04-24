package qa.guru.tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import qa.guru.pages.RegistrationFormPage;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;

public class RegistrationFormWithPageObjectTests {
    Faker faker = new Faker(new Locale("en"));
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            gender = "Male",
            phoneNumber = faker.number().digits(10),
            year = "2000",
            month = "May",
            day = "17",
            subject = "Maths",
            address = faker.address().fullAddress(),
            hobby = "Sports",
            imgName = "img.png",
            imgPath = "img/" + imgName,
            state = "NCR",
            city = "Delhi";

    String expectedFullName = format("%s %s", firstName, lastName);
    String expectedBirthDate = format("%s %s,%s", day, month, year);
    String expectedStadeAndCity = format("%s %s", state, city);

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
        RegistrationFormPage registrationFormPage = new RegistrationFormPage();


        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(day, month, year)
                .setSubject(subject)
                .setAddress(address)
                .setHobby(hobby)
                .setPicture(imgPath)
                .setState(state)
                .setCity(city)
                .clickSubmit();


        registrationFormPage.checkTitleResult()
                .checkResult("Student Name", expectedFullName)
                .checkResult("Student Email", email)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phoneNumber)
                .checkResult("Date of Birth", expectedBirthDate)
                .checkResult("Subjects", subject)
                .checkResult("Hobbies", hobby)
                .checkResult("Picture", imgName)
                .checkResult("Address", address)
                .checkResult("State and City", expectedStadeAndCity);
    }
}
