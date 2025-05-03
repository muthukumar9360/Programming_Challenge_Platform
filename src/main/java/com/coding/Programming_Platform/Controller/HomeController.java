package com.coding.Programming_Platform.Controller;

import com.coding.Programming_Platform.Model.CodingSets;
import com.coding.Programming_Platform.Model.Userinfo;
import com.coding.Programming_Platform.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    private LoginService service;

    @GetMapping("/")
    public String indexPage(){
        return "redirect:/Index.html";
    }

    @GetMapping("/userdetails")
    @ResponseBody
    public String getUsername(HttpSession http){
        return (String)http.getAttribute("username");
    }

    @GetMapping("/medal")
    @ResponseBody
    public String getMedalCount(HttpSession http){
        String username = (String) http.getAttribute("username");

        if (username == null) {
            return "Error: User not logged in.";
        }

        String dbUrl = "jdbc:mysql://localhost:3306/muthukumar";
        String dbUser = "muthukumar_9360";
        String dbPassword = "Muthukumar12";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement stmt = con.prepareStatement("SELECT coding_medals FROM userinfo WHERE username = ?")) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int medals = rs.getInt("coding_medals");
                    return String.valueOf(medals);
                } else {
                    return "Error: User not found.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpSession http){
        http.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession http) {
        System.out.println(email+" "+username+" "+password);
        Userinfo user = service.validateLogin(email,username, password);

        if (user != null) {
            http.setAttribute("username",username);
            http.setAttribute("medals",String.valueOf(user.getCodingmedals()));
            return "redirect:/ProgramSets.html";
        }
        else {
            return "redirect:/Login.html?error=true";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("firstname") String firstname,
                         @RequestParam("lastname") String lastname,
                         @RequestParam("phno") String phno,
                         @RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpSession http) throws IOException {
        System.out.println(email+" "+username+" "+password);
        Userinfo user = service.validateLogin(email,username, password);
        if (user == null) {
            Random random = new Random();
            int otp = 100000 + random.nextInt(900000);
            System.out.println("Generated OTP: " + otp);
            String recipient = email;
            String subject = "OTP VERIFICATION";
            String body = " Your 6 Digit OTP code is : "+otp+"\nfor Registering the Programming and Quiz Platform for this username : "+username;
            http.setAttribute("otp",otp);
            http.setAttribute("username",username);
            http.setAttribute("firstname",firstname);
            http.setAttribute("lastname",lastname);
            http.setAttribute("phno",phno);
            http.setAttribute("email",email);
            http.setAttribute("password",password);
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python", "D:\\Mini_Projects\\Programming_Platform\\src\\main\\java\\com\\coding\\Programming_Platform\\Scripts\\send_email.py", recipient, subject, body
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Python script executed successfully!");
            return "redirect:/VerifyOTP.html";
        }
        else {
            return "redirect:/Login.html";
        }
    }

    @GetMapping("/codingsets")
    @ResponseBody
    public ResponseEntity<?> getAvailsets(HttpSession http){
        String user = (String) http.getAttribute("username");

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<CodingSets> l = service.getAvailableSets();
        return new ResponseEntity<>(l, HttpStatus.OK);
    }
}
