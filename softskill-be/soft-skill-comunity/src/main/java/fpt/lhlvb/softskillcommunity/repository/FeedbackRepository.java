package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Feedback;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FeedbackMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "WITH tbl AS (\n" +
            "SELECT\n" +
            "\tf.id AS feedbackId,\n" +
            "\tb.\"name\" AS feedbackTitle,\n" +
            "\tf.content,\n" +
            "\tf.rate_star AS rateStar,\n" +
            "\tu.\"name\" AS createdName,\n" +
            "\tu.mail AS createdMail,\n" +
            "\tCAST(f.created_date AS DATE) AS createdDate,\n" +
            "\tf.status,\n" +
            "\tf.content_response AS contentResponse,\n" +
            "\tu1.name AS updatedName\n" +
            "FROM\n" +
            "\tfeedback f\n" +
            "JOIN users u ON\n" +
            "\tf.created_by = u.\"id\"\n" +
            "JOIN users u1 ON\n" +
            "\tf.updated_by = u1.\"id\"\n" +
            "JOIN feedback_title b ON\n" +
            "\tf.title_id = b.\"id\"\n" +
            "WHERE\n" +
            "\tf.delete_flag = FALSE\n" +
            "ORDER BY\n" +
            "\tstatus ASC,\n" +
            "\tf.created_date ASC\n" +
            ")\n" +
            "SELECT\n" +
            "\tROW_NUMBER() OVER () AS numRow ,\n" +
            "\ttbl.*\n" +
            "FROM\n" +
            "\ttbl", nativeQuery = true)
    List<FeedbackMapping> loadFeedback();

    Optional<Feedback> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);
}
