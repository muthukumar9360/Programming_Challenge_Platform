package com.coding.Programming_Platform.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.sql.*;
import java.util.*;

@Service
public class CodeExecutionService {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public JSONObject execute(String username, String code, String language, int qid) {
        JSONObject jsonResponse = new JSONObject();

        try {
            List<String[]> testCases = getTestCases(qid);
            boolean allPassed = true;

            JSONArray resultsArray = new JSONArray();
            for (String[] testCase : testCases) {
                String input = testCase[0];
                String expectedOutput = testCase[1];

                String executionResult = executeCode(language, code, input);
                JSONObject resultObj = new JSONObject();
                resultObj.put("input", input);
                resultObj.put("expected", expectedOutput);
                resultObj.put("output", executionResult.trim());

                if (!executionResult.trim().equals(expectedOutput.trim())) {
                    allPassed = false;
                    resultObj.put("status", "Failed");
                } else {
                    resultObj.put("status", "Passed");
                }
                resultsArray.put(resultObj);
            }

            jsonResponse.put("results", resultsArray);

            if (allPassed) {
                markQuestionAsCompleted(username, qid, language, code);
                jsonResponse.put("status", "Completed");
            } else {
                jsonResponse.put("status", "Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("error", "Error: " + e.getMessage());
        }

        return jsonResponse;
    }

    private List<String[]> getTestCases(int qid) throws Exception {
        List<String[]> testCases = new ArrayList<>();

        String dbURL = url;
        String dbUsername = username;
        String dbPassword = password;

        if (dbURL == null || dbUsername == null || dbPassword == null) {
            throw new Exception("Database connection parameters not found in context.");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement("SELECT input_data, expected_output FROM CodingTestCases WHERE qid = ?")) {
            stmt.setInt(1, qid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                testCases.add(new String[]{rs.getString("input_data"), rs.getString("expected_output")});
            }
        }

        return testCases;
    }

    private String executeCode(String language, String code, String input) throws IOException {
        String extension, compileCmd = "", runCmd = "";
        boolean needsCompilation = false;

        switch (language.toLowerCase()) {
            case "java":
                extension = ".java";
                compileCmd = "javac Temp.java";
                runCmd = "java Temp";
                needsCompilation = true;
                break;
            case "python":
                extension = ".py";
                runCmd = "python3 Temp.py";
                break;
            case "cpp":
                extension = ".cpp";
                compileCmd = "g++ Temp.cpp -o temp";
                runCmd = "./temp";
                needsCompilation = true;
                break;
            case "c":
                extension = ".c";
                compileCmd = "gcc Temp.c -o temp";
                runCmd = "./temp";
                needsCompilation = true;
                break;
            default:
                return "Unsupported language.";
        }

        File tempFile = new File("Temp" + extension);
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(code);
        }

        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");
        if (isWindows) {
            runCmd = switch (language.toLowerCase()) {
                case "java" -> "java Temp";
                case "python" -> "python Temp.py";
                default -> "temp.exe";
            };
        }

        if (needsCompilation) {
            ProcessBuilder compilePB = isWindows ?
                    new ProcessBuilder("cmd.exe", "/c", compileCmd) :
                    new ProcessBuilder("/bin/sh", "-c", compileCmd);

            String compileOutput = executeProcess(compilePB);
            if (!compileOutput.isEmpty()) {
                tempFile.delete();
                return "Compilation Error:\n" + compileOutput;
            }
        }

        ProcessBuilder runPB = isWindows ?
                new ProcessBuilder("cmd.exe", "/c", runCmd) :
                new ProcessBuilder("/bin/sh", "-c", runCmd);

        String executionOutput = executeProcessWithInput(runPB, input);
        tempFile.delete();
        return executionOutput.trim();
    }

    private String executeProcess(ProcessBuilder pb) throws IOException {
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }

    private String executeProcessWithInput(ProcessBuilder pb, String input) throws IOException {
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write(input);
        writer.newLine();
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }

    private void markQuestionAsCompleted(String un, int qid, String language, String code) throws Exception {
        String urlt = url;
        String usert = username;
        String passwordt = password;

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(urlt, usert, passwordt)) {

            boolean isFirstSuccessfulAttempt = true;

            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM usercodingscores WHERE username=? AND quesid=? AND language=?"
            );
            stmt.setString(1, un);
            stmt.setInt(2, qid);
            stmt.setString(3, language);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                isFirstSuccessfulAttempt = false;
                stmt = con.prepareStatement("UPDATE usercodingscores SET code=?, submitted_at=NOW() " +
                        "WHERE username=? AND quesid=? AND language=?");
                stmt.setString(1, code);
                stmt.setString(2, un);
                stmt.setInt(3, qid);
                stmt.setString(4, language);
                stmt.executeUpdate();
            } else {
                stmt = con.prepareStatement("INSERT INTO usercodingscores (username, quesid, language, code, submitted_at) " +
                        "VALUES (?, ?, ?, ?, NOW())");
                stmt.setString(1, un);
                stmt.setInt(2, qid);
                stmt.setString(3, language);
                stmt.setString(4, code);
                stmt.executeUpdate();
            }

            if (isFirstSuccessfulAttempt) {
                stmt = con.prepareStatement("UPDATE userinfo SET coding_medals = coding_medals + 1 WHERE username=?");
                stmt.setString(1, un);
                stmt.executeUpdate();
            }

        }
    }
}

