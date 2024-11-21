package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.getUsername().isBlank()||account.getPassword().length()<4||accountDAO.accountExists(account.getAccount_id())==false){
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    public Account login (Account account){

        return accountDAO.login(account);


    }



}
