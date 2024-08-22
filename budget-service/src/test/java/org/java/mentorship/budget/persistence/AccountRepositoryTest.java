package org.java.mentorship.budget.persistence;

import org.h2.tools.Server;
import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.persistence.mapper.AccountRowMapper;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        AccountRepository.class,
        AccountRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
})
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<BankAccountEntity> results = accountRepository.findAll();

        // Then
        assertEquals(4, results.size());
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setUserId(1);
        account.setName("New Checking Account");
        account.setType(AccountType.CHECKING);
        account.setBalance(1500);
        account.setCurrency("USD");

        // When
        accountRepository.save(account);

        // Then
        List<BankAccountEntity> result = accountRepository.findAll();
        assertEquals(5, result.size());
        assertTrue(result.stream().anyMatch(a -> "New Checking Account".equals(a.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectAccount() {
        // Given
        BankAccountEntity account = accountRepository.findAll().get(0);

        // When
        BankAccountEntity result = accountRepository.findById(account.getId());

        // Then
        assertNotNull(result);
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getName(), result.getName());
        assertEquals(account.getUserId(), result.getUserId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        BankAccountEntity account = accountRepository.findAll().get(0);
        account.setName("Updated Account Name");
        account.setBalance(2000);

        // When
        accountRepository.update(account);

        // Then
        BankAccountEntity updatedAccount = accountRepository.findById(account.getId());
        assertEquals("Updated Account Name", updatedAccount.getName());
        assertEquals(2000, updatedAccount.getBalance());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        BankAccountEntity account = accountRepository.findAll().get(1);

        // When
        accountRepository.delete(account.getId());

        // Then
        List<BankAccountEntity> results = accountRepository.findAll();
        assertEquals(3, results.size());
        assertFalse(results.stream().anyMatch(a -> a.getId().equals(account.getId())));
    }
}
