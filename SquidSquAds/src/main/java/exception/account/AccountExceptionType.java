package exception.account;

public enum AccountExceptionType {
    ACCOUNT_NOT_FOUND("Compte inexistant"),
    WRONG_PASSWORD("Courriel ou mot de passe erroné"),
    ADD_ACCOUNT("Compte crée"),
    ;

    private final String description;

    AccountExceptionType(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
