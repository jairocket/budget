Budget is an API for expenses control

## Swagger

```
http://localhost:8080/swagger-ui/index.html
```

## Business Rules

### Domain

#### User

- Should have a `name` with min of three and max of 60 characters;
- Should have a valid `email`;
- Should have a unique `email`;
- Should have a strong `password` with;
    - At least one special character;
    - At least one digit;
    - At least one lowercase letter;
    - At least one uppercase letter;
    - Minimum of eight and maximum of twenty characters;
    - Should be hashed before stored;
- Should have a `role`;
    - Regular users should be able to manage their own boards;
    - Admin users should be able to manage all api resources;
- Can have one or more `Boards`
- Should be authenticated and should have role ADMIN to create new ADMIN user.

#### Board

- Each `Board` should be related to one `User`;
- Can have zero or lots of `Expenses`
- Can have zero or lots of `Incomes`

#### Events

- Refer to anything that can have an impact on your personal finances;
- Should have a `title` with min of 3 and max of 45 characters;
- Can have a short `description` with maximum of 256 characters;
    - If `description` is not informed, it should be stored as an empty string;
- Should have a `value`;
    - `value` should have be formatted with two decimal digits before stored;
- Should have a `dueDate`
- Should have one or more `Categories`

#### Expense

- `Expenses` are a type of `Event`
- Should have a `status`:
    - PENDING, if it is not paid, but you still have time to do it;
    - PAID, if it is... paid;
    - LATE, if it is not paid, but due date has expired.

#### Income

- `Incomes` are a type of `Event`
- Should have a `status`:
    - PENDING, if it is not received, but the due date has not expired yet;
    - RECEIVED, if it is... received;
    - LATE, if it is not received, but due date has expired.
