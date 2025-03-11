package com.example.swfwmd_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PermitController {

    @Autowired
    private PermitRepository permitRepository;

    @GetMapping("/")
    @ResponseBody
    public String showForm() {
        // Simple HTML form with fields for name, address, city, state, zip, permit type
        return "<html>"
             + "<head><title>SWFWMD Permitting</title></head>"
             + "<body>"
             + "  <h1>Welcome to <i style='color:blue'>SWFWMD</i> Permitting</h1>"
             + "  <form method='POST' action='/submit'>"
             + "    <label>Name:</label><br/>"
             + "    <input type='text' name='userName' /><br/><br/>"

             + "    <label>Street:</label><br/>"
             + "    <input type='text' name='street' /><br/><br/>"

             + "    <label>City:</label><br/>"
             + "    <input type='text' name='city' /><br/><br/>"

             + "    <label>State:</label><br/>"
             + "    <input type='text' name='state' /><br/><br/>"

             + "    <label>Zip:</label><br/>"
             + "    <input type='text' name='zip' /><br/><br/>"

             + "    <label>Permit Type:</label><br/>"
             + "    <select name='permitType'>"
             + "      <option value='Water Use Permit'>Water Use Permit</option>"
             + "      <option value='Environmental Resource Permit'>Environmental Resource Permit</option>"
             + "    </select><br/><br/>"

             + "    <button type='submit'>Submit</button>"
             + "  </form>"
             + "</body>"
             + "</html>";
    }

    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmission(
            @RequestParam String userName,
            @RequestParam String street,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zip,
            @RequestParam String permitType
    ) {
        // 1. Create the Address object
        Address addressObj = new Address(street, city, state, zip);

        try {
            // 2. Validate via USPS
            UspsApiClient uspsClient = new UspsApiClient();
            Address validatedAddress = uspsClient.validateAddress(addressObj);

            // 3. Create Permit entity with validated address
            Permit permit = new Permit();
            permit.setUserName(userName);
            permit.setPermitType(permitType);
            permit.setDateSubmitted(java.time.LocalDateTime.now());
            permit.setAddress(validatedAddress);

            // 4. Save to DB
            Permit savedPermit = permitRepository.save(permit);

            // 5. Return confirmation
            return "<html><body>"
                 + "<h2>Thank you, " + userName + "!</h2>"
                 + "<p>We received your application. Permit ID: " + savedPermit.getId() + "</p>"
                 + "<p>Address validated to ZIP: " + validatedAddress.getZip() + "</p>"
                 + "<p>Permit type: " + permitType + "</p>"
                 + "</body></html>";
        } catch (Exception e) {
            // Handle exceptions if USPS call fails
            return "<html><body>"
                 + "<h2>Error submitting permit</h2>"
                 + "<p>" + e.getMessage() + "</p>"
                 + "</body></html>";
        }
    }
}
