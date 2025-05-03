package com.coding.Programming_Platform.Repository;

import com.coding.Programming_Platform.Model.CodingQuestion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;
import java.sql.*;

@Repository
public class ManualRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public List<CodingQuestion> getQuestionsBySetId(int setId, String username) throws SQLException {
        List<CodingQuestion> questions = new ArrayList<>();

        String query = """
                SELECT q.qid, q.question_title, cs.set_name, 
                       CASE WHEN u.username IS NOT NULL THEN 1 ELSE 0 END AS completed
                FROM CodingQuestions q
                JOIN CodingSetQuestions cq ON q.qid = cq.qid
                JOIN CodingSets cs ON cq.set_id = cs.set_id
                LEFT JOIN UserCodingScores u ON q.qid = u.quesid AND u.username = ?
                WHERE cq.set_id = ?
                """;

        try (
                Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                PreparedStatement pst = con.prepareStatement(query)
        ) {
            pst.setString(1, username);
            pst.setInt(2, setId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    CodingQuestion q = new CodingQuestion();
                    q.setQid(rs.getInt("qid"));
                    q.setQuestionTitle(rs.getString("question_title"));
                    q.setSetName(rs.getString("set_name"));
                    q.setCompleted(rs.getInt("completed") == 1);
                    questions.add(q);
                    System.out.println(q);
                }
            }
        }

        return questions;
    }
}
