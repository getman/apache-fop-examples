package apache.fop.converter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

import lombok.*;

/**
 * Class description
 * author: aparfenov
 * date: 4/9/23
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "caseFileId", "date", "name", "surname" })
@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessagePdfInputDataDAO {
    private int caseFileId;
    private Date date;
    private String name;
    private String surname;

}
