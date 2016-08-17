package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.Amount;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.bind.ValidationException;
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

  @Mock
  DataStore dataStore;

  private AccountRepository accountRepository;
  private String username = "Ivan";
  private Double amount;

  public PersistentAccountRepositoryTest(Double amount) {
    this.amount = amount;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
            {10.0}, {110.0}, {243.0}
    });
  }

  @Before
  public void setUp() {
    accountRepository = new PersistentAccountRepository(dataStore);
  }

  @Test
  public void depositFunds() throws ValidationException {
    List<Double> depositedResult = new ArrayList<>();
    depositedResult.add(amount);

    context.checking(new Expectations() {{
      oneOf(dataStore).executeQuery("UPDATE account SET balance=balance+? WHERE username=?", new Object[]{amount, "Ivan"});

      oneOf(dataStore).fetchRows(with(equalTo("SELECT balance FROM account WHERE username=?")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"Ivan"})));
      will(returnValue(depositedResult));
    }});
    Double afterDeposit = accountRepository.deposit(new Amount(username, amount));
    Double depositedAmount = afterDeposit;

    assertThat(depositedAmount, is(equalTo(amount)));
  }

  @Test
  public void withdrawFunds() throws ValidationException {
    List<Double> beforeWithdraw = new ArrayList<>();
    beforeWithdraw.add(amount * 2);

    List<Double> withdrawResult = new ArrayList<>();
    withdrawResult.add(amount);

    context.checking(new Expectations() {{
      oneOf(dataStore).fetchRows(with(equalTo("SELECT balance FROM account WHERE username=?")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"Ivan"})));
      will(returnValue(beforeWithdraw));

      oneOf(dataStore).executeQuery("UPDATE account SET balance=balance-? WHERE username=?", new Object[]{amount, "Ivan"});

      oneOf(dataStore).fetchRows(with(equalTo("SELECT balance FROM account WHERE username=?")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"Ivan"})));
      will(returnValue(withdrawResult));
    }});

    Double withdrawnAmount = accountRepository.withdraw(new Amount(username, amount));

    assertThat(withdrawnAmount, is(equalTo(amount)));
  }

}