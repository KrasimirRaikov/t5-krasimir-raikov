package com.clouway.bank.persistence;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.Amount;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import com.clouway.bank.utils.AccountRepositoryUtility;
import com.clouway.bank.utils.DatabaseConnectionRule;
import com.clouway.bank.utils.UserRepositoryUtility;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.bind.ValidationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@RunWith(Parameterized.class)
public class PersistentAccountRepositoryTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Rule
    public DatabaseConnectionRule connectionRule = new DatabaseConnectionRule("bank_test");

    @Mock
    DataStore dataStore;

    private AccountRepository accountRepository;
    private Connection connection;
    private AccountRepositoryUtility accountRepositoryUtility;
    private UserRepositoryUtility userRepositoryUtility;
    private String username = "Ivan";
    private Double amount;

    public PersistentAccountRepositoryTest(Double amount) {
        this.amount = amount;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {10.0}, {110.0}, {243.0}, {315.0}, {46.0}, {520.0}, {63.0}
        });
    }

    @Before
    public void setUp() {
        accountRepository = new PersistentAccountRepository(dataStore);
        connection = connectionRule.getConnection();
        accountRepositoryUtility = new AccountRepositoryUtility(connection);
        accountRepositoryUtility.clearAccountTable();
        userRepositoryUtility = new UserRepositoryUtility(connection);
        userRepositoryUtility.clearUsersTable();
        userRepositoryUtility.registerUser("Ivan", "123456");
    }

    @After
    public void tearDown() throws SQLException {
        accountRepositoryUtility.clearAccountTable();
        userRepositoryUtility.clearUsersTable();
        connection.close();
    }

    @Test
    public void depositFunds() throws ValidationException {
        List<Double> queryResult = new ArrayList<>();
        queryResult.add(0D);

        List<Double> depositedResult = new ArrayList<>();
        depositedResult.add(amount);

        context.checking(new Expectations() {{
            oneOf(dataStore).executeQuery("INSERT INTO account(username, balance) VALUES(?, ?);", new Object[]{"Ivan", 0D});

            oneOf(dataStore).fetchRows(with(equalTo("SELECT balance FROM account WHERE username=?")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"Ivan"})));
            will(returnValue(queryResult));

            oneOf(dataStore).executeQuery("UPDATE account SET balance=balance+? WHERE username=?", new Object[]{amount, "Ivan"});

            oneOf(dataStore).fetchRows(with(equalTo("SELECT balance FROM account WHERE username=?")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"Ivan"})));
            will(returnValue(depositedResult));
        }});
        accountRepository.createAccount("Ivan");

        Double originalAmount = accountRepository.getCurrentBalance(username);

        Double afterDeposit = accountRepository.deposit(new Amount(username, amount));
        Double depositedAmount = afterDeposit - originalAmount;

        assertThat(depositedAmount, is(equalTo(amount)));
    }

}