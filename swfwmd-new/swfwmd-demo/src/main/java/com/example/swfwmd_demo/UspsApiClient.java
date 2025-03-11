package com.example.swfwmd_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

public class UspsApiClient {

    // Replace with your USPS Web Tools user ID
    private static final String USPS_USER_ID = "YOUR_USPS_USER_ID";

    // Example method: calls the "Verify" API to standardize/validate the address
    public Address validateAddress(Address address) throws IOException {
        // 1. Build request XML
        String requestXml = buildRequestXml(address);

        // 2. Build the URL
        String urlString = "https://secure.shippingapis.com/ShippingAPI.dll"
                         + "?API=Verify"
                         + "&XML=" + URLEncoder.encode(requestXml, StandardCharsets.UTF_8);

        // 3. Open connection
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 4. Read response
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String responseXml = sb.toString();

            // 5. Parse the XML to extract the corrected address
            Address validated = parseUspsResponse(responseXml, address);
            return validated;
        }
    }

    private String buildRequestXml(Address address) {
        // Minimal example. Consult USPS docs for exact formatting (AddressValidateRequest).
        // Insert your USPS_USER_ID and the address fields:
        return "<AddressValidateRequest USERID=\"" + USPS_USER_ID + "\">"
             + "<Revision>1</Revision>"
             + "<Address ID=\"0\">"
             +     "<Address1></Address1>"
             +     "<Address2>" + address.getStreet() + "</Address2>"
             +     "<City>" + address.getCity() + "</City>"
             +     "<State>" + address.getState() + "</State>"
             +     "<Zip5>" + address.getZip() + "</Zip5>"
             +     "<Zip4></Zip4>"
             + "</Address>"
             + "</AddressValidateRequest>";
    }

    private Address parseUspsResponse(String responseXml, Address original) {
        // For demonstration, let's pretend we found the same city/state but corrected the ZIP.
        // You would normally parse the XML properly here.
        if (responseXml.contains("<Zip5>")) {
            // Extract the <Zip5> element from the response as an example
            String correctedZip = "90210"; // Hardcoded example
            original.setZip(correctedZip);
        }
        // Optionally update street/city/state if USPS found corrections
        return original;
    }
}
