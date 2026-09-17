# Account Service Study Tasks

## Project Objective

Implement the `account-service` module for the banking system. The service manages customer accounts, balances, account blocking, and balance changes used by the transaction service.

Base controller:

`account-service/src/main/java/com/banking/account_service/controller/AccountController.java`

## Task Breakdown

### Task 1: Implement `createAccount`

**Endpoint:** `POST /api/accounts`

Implement account creation using `CreateAccountRequest`.

#### Requirements

- Validate the request with Jakarta Bean Validation.
- Create and persist a new account.
- Generate or assign a unique account number.
- Set the initial balance.
- Return an `AccountResponse`.
- Return HTTP `201 Created`.
- Reject invalid account data.

#### Acceptance Criteria

- A valid request creates an account.
- The account number is unique.
- The created account is stored in the database.
- Invalid input returns a clear validation error.
- Unit tests cover successful and failed account creation.

---

### Task 2: Implement `getAccount`

**Endpoint:** `GET /api/accounts/{accountNumber}`

Implement retrieval of an account by account number.

#### Requirements

- Search for the account by account number.
- Map the entity to `AccountResponse`.
- Return HTTP `200 OK` when the account exists.
- Throw an account-not-found exception when it does not exist.
- Return HTTP `404 Not Found` for a missing account.

#### Acceptance Criteria

- An existing account can be retrieved.
- A missing account returns `404 Not Found`.
- Sensitive internal fields are not exposed.
- Unit tests cover both scenarios.

---

### Task 3: Implement `getBalance`

**Endpoint:** `GET /api/accounts/{accountNumber}/balance`

Implement retrieval of the current account balance.

#### Requirements

- Find the account by account number.
- Return the balance as `BigDecimal`.
- Return HTTP `200 OK` for an existing account.
- Return HTTP `404 Not Found` when the account does not exist.
- Do not use floating-point types for money.

#### Acceptance Criteria

- The correct balance is returned.
- Missing accounts are handled correctly.
- Balance retrieval has automated tests.

---

### Task 4: Implement `blockAccount`

**Endpoint:** `PUT /api/accounts/{accountNumber}/block`

Implement the account blocking operation.

#### Requirements

- Find the account by account number.
- Change the account status to `BLOCKED`.
- Persist the updated account.
- Return a successful response.
- Prevent blocked accounts from deducting money.
- Handle missing accounts with `404 Not Found`.

#### Acceptance Criteria

- An active account can be blocked.
- The blocked status is persisted.
- A blocked account cannot perform a deduction.
- Tests cover blocking and repeated blocking behavior.

---

### Task 5: Implement `deductBalance`

**Endpoint:** `PUT /api/accounts/{accountNumber}/deduct?amount={amount}`

Implement the balance deduction operation called when a transfer is initiated.

#### Requirements

- Find the account by account number.
- Validate that the amount is greater than zero.
- Reject deductions from blocked accounts.
- Reject deductions when the balance is insufficient.
- Deduct the amount atomically.
- Persist the updated balance.
- Ensure the balance is unchanged when the operation fails.

#### Acceptance Criteria

- A valid deduction decreases the balance correctly.
- Zero and negative amounts are rejected.
- Insufficient funds are rejected.
- Blocked accounts cannot be debited.
- Missing accounts return `404 Not Found`.
- Tests cover successful and failed deductions.

---

### Task 6: Implement `creditBalance`

**Endpoint:** `PUT /api/accounts/{accountNumber}/credit?amount={amount}`

Implement the balance credit operation used for refunds and receiver settlement.

#### Requirements

- Find the account by account number.
- Validate that the amount is greater than zero.
- Add the amount to the current balance.
- Persist the updated balance atomically.
- Handle missing accounts with `404 Not Found`.

#### Acceptance Criteria

- A valid credit increases the balance correctly.
- Zero and negative amounts are rejected.
- The balance is unchanged when validation fails.
- Tests cover refund and receiver-credit scenarios.

---

### Task 7: Implement account business rules

Implement and test the following rules:

- Account numbers must be unique.
- Opening balances cannot be negative.
- Blocked accounts cannot be debited.
- Deductions cannot exceed the available balance.
- Credit amounts must be greater than zero.
- Deduction amounts must be greater than zero.
- Account balances must remain valid after every operation.

---

### Task 8: Implement exception handling

Create centralized exception handling for:

- Account not found.
- Insufficient funds.
- Blocked account.
- Invalid transaction amount.
- Duplicate account number.
- Request validation errors.

Use a consistent error response containing the timestamp, HTTP status, error code, message, and request path.

Example:

```json
{
  "timestamp": "2026-09-17T10:00:00Z",
  "status": 400,
  "error": "INSUFFICIENT_FUNDS",
  "message": "Account does not have enough balance",
  "path": "/api/accounts/123/deduct"
}
```

---

### Task 9: Write `AccountService` unit tests

Add unit tests for:

- Creating an account.
- Retrieving an existing account.
- Retrieving a missing account.
- Retrieving an account balance.
- Blocking an account.
- Deducting money successfully.
- Rejecting deductions from blocked accounts.
- Rejecting deductions with insufficient funds.
- Rejecting zero or negative deductions.
- Crediting money successfully.
- Rejecting zero or negative credits.

---

### Task 10: Write controller integration tests

Test all account endpoints:

```text
POST /api/accounts
GET /api/accounts/{accountNumber}
GET /api/accounts/{accountNumber}/balance
PUT /api/accounts/{accountNumber}/block
PUT /api/accounts/{accountNumber}/deduct
PUT /api/accounts/{accountNumber}/credit
```

#### Acceptance Criteria

- Successful requests return the expected HTTP status.
- Invalid requests return the expected error response.
- Missing accounts return `404 Not Found`.
- Validation errors use the standard error format.
- Tests run successfully with the project build.

---

## Suggested Implementation Order

1. Review the existing `Account`, DTO, repository, and service classes.
2. Implement `createAccount`.
3. Implement `getAccount`.
4. Implement `getBalance`.
5. Implement `blockAccount`.
6. Implement `deductBalance`.
7. Implement `creditBalance`.
8. Add business exceptions and global exception handling.
9. Add unit tests.
10. Add controller integration tests.
11. Update the README with setup instructions and example API requests.

## Definition of Done

- All six controller operations are implemented.
- Account data is persisted correctly.
- Monetary operations use `BigDecimal`.
- Invalid operations do not corrupt account balances.
- Error responses are consistent.
- Unit and integration tests pass.
- The implementation is documented with example requests.
