package form.userProfile.request;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class CreateUserProfileRequest {

    private String name;
    private String accountNumber;
    private String description;
    private String[] url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
