package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class SslServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslServerApplication.class, args);
    }

    @GetMapping("/checksum")
    public Map<String, String> getChecksum() {
        String name = "Mikaela Spence";
        String data = name + " Check Sum!";
        
        // Perform input validation
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        
        if (!isValidData(data)) {
            throw new IllegalArgumentException("Invalid data: " + data);
        }
        
        String checksum = calculateChecksum(data);
        
        Map<String, String> result = new HashMap<>();
        result.put("name", name);
        result.put("checksum", checksum);
        
        return result;
    }
    
    private boolean isValidName(String name) {
    
        // check if the name is not empty
        return name != null && !name.trim().isEmpty();
    }
    
    private boolean isValidData(String data) {

        // For example, check if the data is not empty
        return data != null && !data.trim().isEmpty();
    }

    private String calculateChecksum(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {

            // Return a meaningful error message to the client
            return "Error: Unable to calculate checksum.";
        }
    }

}
