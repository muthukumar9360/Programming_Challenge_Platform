package com.coding.Programming_Platform.Controller;

import com.coding.Programming_Platform.Model.CodeExecution;
import com.coding.Programming_Platform.Service.CodeExecutionService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeservice;

    @PostMapping("/Executecode")
    public ResponseEntity<String> executeCode(@RequestBody CodeExecution payload, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"User not logged in!\"}");
        }

        String code = payload.getCode();
        String language = payload.getLanguage();
        int qid = payload.getQid();

        System.out.println("Received Code: " + code);
        System.out.println("Language: " + language);
        System.out.println("QID: " + qid);
        System.out.println("Username: " + username);

        JSONObject result = codeservice.execute(username, code, language, qid);

        return ResponseEntity.ok(result.toString());
    }

}
