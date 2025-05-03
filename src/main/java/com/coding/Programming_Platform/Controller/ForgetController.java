package com.coding.Programming_Platform.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.*;
import java.sql.*;
import java.io.*;

@RestController
@RequestMapping("/Forget")
public class ForgetController {

    @Autowired
    private DataSource dataSource;

    @PostMapping
    public Map<String, Object> handleForget(@RequestParam String step,
                                            @RequestParam(required = false) String username,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) String phno,
                                            @RequestParam(required = false) String otp,
                                            @RequestParam(required = false) String newpassword,
                                            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        System.out.println("\n🔹 Step: " + step);
        System.out.println("🔹 Session ID: " + session.getId());

        try (Connection con = dataSource.getConnection()) {

            if ("1".equals(step)) {
                if (username == null || email == null || phno == null ||
                        username.trim().isEmpty() || email.trim().isEmpty() || phno.trim().isEmpty()) {
                    response.put("success", false);
                    response.put("message", "All fields are required!");
                    return response;
                }

                String sql = "SELECT * FROM userinfo WHERE username = ? AND email = ? AND phno = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, phno);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int generatedOtp = 100000 + new Random().nextInt(900000);
                    session.setAttribute("username", username);
                    session.setAttribute("otp", generatedOtp);

                    String subject = "Password Reset OTP";
                    String body = "Your OTP for password reset is: " + generatedOtp;
                    String scriptPath = "D:\\Mini_Projects\\Programming_Platform\\src\\main\\java\\com\\coding\\Programming_Platform\\Scripts\\send_email.py";

                    ProcessBuilder pb = new ProcessBuilder("python", scriptPath, email, subject, body);
                    pb.redirectErrorStream(true);
                    Process process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while (reader.readLine() != null);
                    process.waitFor();

                    response.put("success", true);
                    response.put("message", "OTP Sent Successfully.");
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid User Details!");
                }
            }

            else if ("2".equals(step)) {
                Object sessionOtp = session.getAttribute("otp");
                if (otp == null || sessionOtp == null || !otp.equals(sessionOtp.toString())) {
                    response.put("success", false);
                    response.put("message", "Invalid OTP!");
                } else {
                    response.put("success", true);
                    response.put("message", "OTP Verified! Please enter new password.");
                }
            }

            else if ("3".equals(step)) {
                String sessionUsername = (String) session.getAttribute("username");

                if (sessionUsername == null || newpassword == null || newpassword.trim().isEmpty()) {
                    response.put("success", false);
                    response.put("message", "Password reset failed. Missing data.");
                } else {
                    String sql = "UPDATE userinfo SET password = ? WHERE username = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, newpassword); // Consider hashing this in production
                    ps.setString(2, sessionUsername);
                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        response.put("success", true);
                        response.put("message", "Password updated successfully!");
                    } else {
                        response.put("success", false);
                        response.put("message", "Failed to update password.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Internal error: " + e.getMessage());
        }

        return response;
    }
}

