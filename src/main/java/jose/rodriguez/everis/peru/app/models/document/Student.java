package jose.rodriguez.everis.peru.app.models.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Data
@Document(collection = "students")
public class Student {
  @Id
  private String id;
  @NotEmpty
  private String name;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String gender;
  @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
  private Date date;
  @NotEmpty

  private String typeDocument;
  @NotNull
  private int document;

  public Student() {}

  /**
   * a.
   */


  public Student(String name, String lastName, String gender, String typeDocument, int document) {

    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
    this.typeDocument = typeDocument;
    this.document = document;
  }



}
