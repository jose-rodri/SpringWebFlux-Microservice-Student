package jose.rodriguez.everis.peru.app.models.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Getter
@Setter
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
  
  private int note;

  public Student() {
   
  }

  public Student( String name,  String lastName,  String gender,
      Date date,  String typeDocument,  int document, int note) {
 
    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
    this.date = date;
    this.typeDocument = typeDocument;
    this.document = document;
    this.note = note;
  }
  
  
  
  
  /*
  
  public Student( String name,  String lastName,  String gender,
      Date date,  String typeDocument,  int document) {
   
    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
    this.date = date;
    this.typeDocument = typeDocument;
    this.document = document;
  }
*/
 


}
