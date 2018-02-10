package model;


import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Date;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Component
@Entity
@Table(name = "profildutilisateur")
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numero_profilutilisateur")
    private long profileID;

    @Column(name = "numero_compte")
    private int accountNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_creation")
    private Date creationTime;

    @Column(name = "url")
    private String[] url;

    // CONSTRUCTOR

    public UserProfile(){}

    public UserProfile(int accountNumber, String name, String description, String[] url) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.description = description;
        this.url = new String[url.length];

        for(int i = 0; i < url.length; i++){
          this.url[i] = url[i];
        }
    }

    // JPA required method

    @PrePersist
    void creationTimeStamp() {
        this.creationTime = new Date();
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
