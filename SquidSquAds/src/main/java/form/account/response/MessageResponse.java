package form.account.response;

import form.account.Form;

public class MessageResponse extends Form{
    private String message;

    public MessageResponse(int status, String message) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
