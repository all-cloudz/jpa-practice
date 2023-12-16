package com.practice.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootTest
class TransactionPractices {

    @Autowired
    private PlatformTransactionManager txManager;

    @Test
    void practice1_getTransaction() {
        final TransactionStatus transactionStatus = txManager.getTransaction(new DefaultTransactionDefinition());
        assertThat(transactionStatus).isNotNull();
    }
}
