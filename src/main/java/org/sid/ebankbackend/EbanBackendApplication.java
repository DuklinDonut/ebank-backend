package org.sid.ebankbackend;

import org.sid.ebankbackend.entities.*;
import org.sid.ebankbackend.enums.AccountStatus;
import org.sid.ebankbackend.enums.OperationType;
import org.sid.ebankbackend.repositories.AccountOperationRepository;
import org.sid.ebankbackend.repositories.BankAccountRepository;
import org.sid.ebankbackend.repositories.CustomerRepository;
import org.sid.ebankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbanBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbanBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankService bankService){
        return  args ->{
            bankService.consulter();
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                org.sid.ebankbackend.entities.Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                 CurrentAccount currentAccount=new CurrentAccount();
                 currentAccount.setId(UUID.randomUUID().toString());
                 currentAccount.setBalance(Math.random()*90000);
                 currentAccount.setCreatedAt(new Date());
                 currentAccount.setStatus(AccountStatus.CREATED);
                 currentAccount.setCustomer(cust);
                 currentAccount.setOverDraft(9000);
                 bankAccountRepository.save(currentAccount);

                 SavingAccount savingAccount=new SavingAccount();
                 savingAccount.setId(UUID.randomUUID().toString());
                 savingAccount.setBalance(Math.random()*90000);
                 savingAccount.setStatus(AccountStatus.CREATED);
                 savingAccount.setCustomer(cust);
                 savingAccount.setInterestRate(5.5);
                 bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<5;i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };
    }

}
