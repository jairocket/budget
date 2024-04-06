Budget is an API for expenses control

## Business Rules

### Domain

#### User

- Should have a `name` with at least three characters;
- Should have a valid `email`;
- Should have a strong `password` with;
    - At least one special character;
    - At least one digit;
    - At least one lowercase letter;
    - At least one uppercase letter;
    - Minimum of eight and maximum of twenty characters;
    - Should be hashed before stored;
- Should have a `role`;
    - Regular users should be able to manage their own boards;
    - Admin users should be able to manage all api resources.
- Can have one or more `Boards`

#### Board

- Each `Board` should be related to one `User`;
- Can have zero or lots of `Expenses`
- Can have zero or lots of `Incomes`
- Should be able to calculate the balance between `Expenses` and `Incomes`;

#### Financial Occurrence

- Refer to anything that can have an impact on your personal finances;
- Should have a `title` with at least three characters;
- Can have a short `description` with maximum of 256 characters;
- Should have a `value`;
    - `value` should have be formatted with two decimal digits before stored;
- Should have a `dueDate`
- Should have one or more `Categories`

#### Expense

- `Expenses` are a type of `FinancialOccurences`
- Should have a `status`:
    - PENDING, if it is not paid, but you still have time to do it;
    - PAID, if it is... paid;
    - LATE, if it is not paid, but due date has expired.

#### Income

- `Incomes` are a type of `FinancialOccurences`
- Should have a `status`:
    - PENDING, if it is not received, but the due date has not expired yet;
    - RECEIVED, if it is... received;
    - LATE, if it is not received, but due date has expired.
