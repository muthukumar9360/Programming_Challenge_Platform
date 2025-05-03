package com.coding.Programming_Platform.Controller;

import com.coding.Programming_Platform.Model.Userinfo;
import com.coding.Programming_Platform.Service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import com.zaxxer.hikari.HikariDataSource;

@Controller
public class VerifyOTPController {

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private LoginService service;

    @PostMapping("/VerifyOTP")
    @ResponseBody
    public void verifyOTP(HttpServletRequest req, HttpServletResponse res, HttpSession session) {

        try {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            Integer otp = (Integer) session.getAttribute("otp");
            String firstname=(String)session.getAttribute("firstname");
            String lastname=(String)session.getAttribute("lastname");
            String phno=(String)session.getAttribute("phno");
            String email=(String)session.getAttribute("email");
            String username=(String)session.getAttribute("username");
            String password=(String)session.getAttribute("password");

            String enteredOtp = req.getParameter("otp1") +
                    req.getParameter("otp2") +
                    req.getParameter("otp3") +
                    req.getParameter("otp4") +
                    req.getParameter("otp5") +
                    req.getParameter("otp6");

            int userOtp;
            try {
                userOtp = Integer.parseInt(enteredOtp);
                System.out.println(userOtp);
            } catch (NumberFormatException e) {
                out.println("<script>alert('Invalid OTP format! Please enter numbers only.'); window.location='VerifyOTP.html';</script>");
                return;
            }

            if (userOtp == otp) {

                try (Connection con = dataSource.getConnection()) {
                    Userinfo newUser = new Userinfo(firstname, lastname, phno, email, username, password, 0, LocalDateTime.now());
                    service.saveuser(newUser);
                    session.removeAttribute("firstname");
                    session.removeAttribute("lastname");
                    session.removeAttribute("email");
                    session.removeAttribute("password");
                    session.removeAttribute("phno");
                }

                out.println("<script>alert('OTP Verified Successfully! ACCOUNT CREATED... LOGIN INTO YOUR ACCOUNT'); window.location='Login.html';</script>");
                session.invalidate();
            } else {
                out.println("<script>alert('Incorrect OTP! Please try again.'); window.location='VerifyOTP.html';</script>");
            }
        } catch (Exception e) {
            try {
                res.getWriter().println("<script>alert('Server Error! Please try again.'); window.location='index.html';</script>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}

