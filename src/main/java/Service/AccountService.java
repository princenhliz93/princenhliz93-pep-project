package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    // Default constructor that initializes the AccountDAO instance

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Constructor that allows injecting a specific AccountDAO instance (useful for testing)
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Adds a new account if the provided account details are valid.
     * - Ensures the username is not blank.
     * - Ensures the password length is at least 4 characters.
     * - Ensures the account ID does not already exist in the database.
     * 
     * @param account The Account object containing the user's details.
     * @return The registered Account object if valid; otherwise, returns null.
     */
    public Account createAccount(Account account){
        if(account.getUsername().isBlank()||account.getPassword().length()<4||accountDAO.accountExists(account.getAccount_id())==false){
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    /**
     * Logs in a user by validating the account credentials.
     * 
     * @param account The Account object containing the login credentials.
     * @return The authenticated Account object if credentials match; otherwise, returns null.
     */
    public Account login (Account account){

        return accountDAO.login(account);


    }



}
