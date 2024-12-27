Implementation of bank account kata

| Method | URL         | Description                      |
|--------|-------------|----------------------------------|
| `GET`  | `/history`  | Retrieve all operations history. |
| `POST` | `/deposit`  | Deposit in your account.         |
| `POST` | `/withdraw` | Withdraw in your account.        |

At present, this application is not yet prod-ready, so we can envisage the following steps to make it deployable in production :<br/>
1/ Adding dev and prod environment variables to the project via application-dev.properties and application-prod.properties files<br/>
2/ Timezone management for Date Operation storage (defining a timezone in application.properties)<br/>
3/ Language : Spring Boot internationalization<br/>
4/ Managing user identification and authentication with Spring Security<br/>
5/ Adding logging for error tracking and application monitoring with Spring Boot Actuator<br/>
6/ Containerisation with Docker for deployment and managing application secrets (database usr/pwd...)<br/>
7/ DevOps chain : CI/CD pipelines for testing, compiling, packaging, delivering and deploying (with Gitlab CI/CD)<br/>

Bank account kata

Think of your personal bank account experience. When in doubt, go for the simplest solution

Requirements

·         Deposit and Withdrawal

·         Account statement (date, amount, balance)

·         Statement printing

The expected result is a service API, and its underlying implementation, that meets the expressed needs.<br/>
Nothing more, especially no UI, no persistence.

User Stories

US 1:

In order to save money<br/>
As a bank client<br/>
I want to make a deposit in my account

US 2:

In order to retrieve some or all of my savings<br/>
As a bank client<br/>
I want to make a withdrawal from my account

US 3:

In order to check my operations<br/>
As a bank client<br/>
I want to see the history (operation, date, amount, balance) of my operations
